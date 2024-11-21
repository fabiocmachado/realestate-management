package com.realestate.service;

import com.realestate.dto.UrbanLandDTO;
import com.realestate.entity.person.Agent;
import com.realestate.entity.person.Seller;
import com.realestate.entity.property.urban.residential.UrbanLand;
import com.realestate.mapper.UrbanLandMapper;
import com.realestate.repository.AgentRepository;
import com.realestate.repository.SellerRepository;
import com.realestate.repository.UrbanLandRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UrbanLandService {

    private final SellerRepository sellerRepository;
    private final UrbanLandRepository urbanLandRepository;
    private final AgentRepository agentRepository;
    private final UrbanLandMapper urbanLandMapper;

    @Transactional
    public UrbanLandDTO createUrbanLand(UrbanLandDTO urbanLandDTO) {
        Seller seller = sellerRepository.findById(urbanLandDTO.getSellerId())
                .orElseThrow(() -> new EntityNotFoundException("Seller not found with ID: " + urbanLandDTO.getSellerId()));
        Agent agent = agentRepository.findById(urbanLandDTO.getAgentId())
                .orElseThrow(() -> new EntityNotFoundException("Agent not found with ID: " + urbanLandDTO.getAgentId()));
        UrbanLand urbanLand = urbanLandMapper.toEntity(urbanLandDTO, seller, agent);
        UrbanLand savedUrbanLand = urbanLandRepository.save(urbanLand);
        return urbanLandMapper.toDTO(savedUrbanLand);
    }


    @Transactional(readOnly = true)
    public UrbanLandDTO getUrbanLandByCode(String propertyCode) {
        UrbanLand urbanLand = urbanLandRepository.findByPropertyCode(propertyCode)
                .orElseThrow(() -> new EntityNotFoundException("Urban Land not found with code: " + propertyCode));
        return urbanLandMapper.toDTO(urbanLand);
    }

    @Transactional(readOnly = true)
    public Page<UrbanLandDTO> getAllUrbanLands(Pageable pageable) {
        return urbanLandRepository.findAll(pageable)
                .map(urbanLandMapper::toDTO);
    }

    @Transactional
    public UrbanLandDTO updateUrbanLand(String propertyCode, UrbanLandDTO urbanLandDTO) {
        UrbanLand existingUrbanLand = urbanLandRepository.findByPropertyCode(propertyCode)
                .orElseThrow(() -> new EntityNotFoundException("Urban Land not found with code: " + propertyCode));

        Seller seller = sellerRepository.findById(urbanLandDTO.getSellerId())
                .orElseThrow(() -> new EntityNotFoundException("Seller not found with ID: " + urbanLandDTO.getSellerId()));
        Agent agent = agentRepository.findById(urbanLandDTO.getAgentId())
                .orElseThrow(() -> new EntityNotFoundException("Agent not found with ID: " + urbanLandDTO.getAgentId()));

        urbanLandMapper.updateEntity(existingUrbanLand, urbanLandDTO, seller, agent);
        UrbanLand updatedUrbanLand = urbanLandRepository.save(existingUrbanLand);
        return urbanLandMapper.toDTO(updatedUrbanLand);
    }


    @Transactional
    public void deleteUrbanLand(String propertyCode) {
        UrbanLand urbanLand = urbanLandRepository.findByPropertyCode(propertyCode)
                .orElseThrow(() -> new EntityNotFoundException("Urban Land not found with code: " + propertyCode));
        urbanLandRepository.delete(urbanLand);
    }
}
