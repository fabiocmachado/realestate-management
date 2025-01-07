package com.realestate.service;

import com.realestate.dto.TownhouseDTO;
import com.realestate.entity.person.Agent;
import com.realestate.entity.person.Seller;
import com.realestate.entity.property.urban.residential.Townhouse;
import com.realestate.mapper.TownhouseMapper;
import com.realestate.repository.AgentRepository;
import com.realestate.repository.TownhouseRepository;
import com.realestate.repository.SellerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TownhouseService {

    private final SellerRepository sellerRepository;
    private final TownhouseRepository townhouseRepository;
    private final AgentRepository agentRepository;
    private final TownhouseMapper townhouseMapper;

    @Transactional
    public TownhouseDTO createTownhouse(TownhouseDTO townhouseDTO) {
        Seller seller = sellerRepository.findById(townhouseDTO.getSellerId())
                .orElseThrow(() -> new EntityNotFoundException("Seller not found with ID: " + townhouseDTO.getSellerId()));
        Agent agent = agentRepository.findById(townhouseDTO.getAgentId())
                .orElseThrow(() -> new EntityNotFoundException("Agent not found with ID: " + townhouseDTO.getAgentId()));
        Townhouse townhouse = townhouseMapper.toEntity(townhouseDTO);
        Townhouse savedTownhouse = townhouseRepository.save(townhouse);
        return townhouseMapper.toDTO(savedTownhouse);
    }

    @Transactional(readOnly = true)
    public TownhouseDTO getTownhouseByCode(String propertyCode) {
        Townhouse townhouse = townhouseRepository.findByPropertyCode(propertyCode);
        if (townhouse == null) {
            throw new EntityNotFoundException("Townhouse not found with code: " + propertyCode);
        }
        return townhouseMapper.toDTO(townhouse);
    }

    @Transactional(readOnly = true)
    public Page<TownhouseDTO> getAllTownhouses(Pageable pageable) {
        return townhouseRepository.findAll(pageable).map(townhouseMapper::toDTO);
    }

    @Transactional
    public TownhouseDTO updateTownhouse(String propertyCode, TownhouseDTO townhouseDTO) {
        Townhouse existingTownhouse = townhouseRepository.findByPropertyCode(propertyCode);
        if (existingTownhouse == null) {
            throw new EntityNotFoundException("Townhouse not found with code: " + propertyCode);
        }
        Seller seller = sellerRepository.findById(townhouseDTO.getSellerId())
                .orElseThrow(() -> new EntityNotFoundException("Seller not found with ID: " + townhouseDTO.getSellerId()));
        Agent agent = agentRepository.findById(townhouseDTO.getAgentId())
                .orElseThrow(() -> new EntityNotFoundException("Agent not found with ID: " + townhouseDTO.getAgentId()));
        townhouseMapper.updateEntityFromDTO(townhouseDTO, existingTownhouse);
        existingTownhouse.setSeller(seller);
        existingTownhouse.setAgent(agent);
        Townhouse updatedTownhouse = townhouseRepository.save(existingTownhouse);
        return townhouseMapper.toDTO(updatedTownhouse);
    }

    @Transactional
    public void deleteTownhouse(String propertyCode) {
        Townhouse townhouse = townhouseRepository.findByPropertyCode(propertyCode);
        if (townhouse == null) {
            throw new EntityNotFoundException("Townhouse not found with code: " + propertyCode);
        }
        townhouseRepository.delete(townhouse);
    }
}
