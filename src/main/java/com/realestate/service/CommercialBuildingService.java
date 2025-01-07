package com.realestate.service;

import com.realestate.dto.CommercialBuildingDTO;
import com.realestate.entity.person.Agent;
import com.realestate.entity.person.Seller;
import com.realestate.entity.property.urban.comercial.CommercialBuilding;
import com.realestate.mapper.CommercialBuildingMapper;
import com.realestate.repository.AgentRepository;
import com.realestate.repository.CommercialBuildingRepository;
import com.realestate.repository.SellerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommercialBuildingService {

    private final SellerRepository sellerRepository;
    private final AgentRepository agentRepository;
    private final CommercialBuildingRepository commercialBuildingRepository;
    private final CommercialBuildingMapper commercialBuildingMapper;

    @Transactional
    public CommercialBuildingDTO createCommercialBuilding(CommercialBuildingDTO commercialBuildingDTO) {
        CommercialBuilding commercialBuilding = commercialBuildingMapper.toEntity(commercialBuildingDTO);
        CommercialBuilding savedCommercialBuilding = commercialBuildingRepository.save(commercialBuilding);
        return commercialBuildingMapper.toDTO(savedCommercialBuilding);
    }

    @Transactional(readOnly = true)
    public CommercialBuildingDTO getCommercialBuildingByCode(String propertyCode) {
        CommercialBuilding commercialBuilding = commercialBuildingRepository.findByPropertyCode(propertyCode);
        if (commercialBuilding == null) {
            throw new EntityNotFoundException("Commercial Building not found with PropertyCode: " + propertyCode);
        }
        return commercialBuildingMapper.toDTO(commercialBuilding);
    }

    @Transactional(readOnly = true)
    public Page<CommercialBuildingDTO> getAllCommercialBuildings(Pageable pageable) {
        return commercialBuildingRepository.findAll(pageable)
                .map(commercialBuildingMapper::toDTO);
    }

    @Transactional
    public CommercialBuildingDTO updateCommercialBuilding(String propertyCode, CommercialBuildingDTO commercialBuildingDTO) {
        CommercialBuilding existingEntity = commercialBuildingRepository.findByPropertyCode(propertyCode);
        if (existingEntity == null) {
            throw new EntityNotFoundException("Commercial Building not found with PropertyCode: " + propertyCode);
        }
        Seller seller = sellerRepository.findById(commercialBuildingDTO.getSellerId())
                .orElseThrow(() -> new EntityNotFoundException("Seller not found with ID: " + commercialBuildingDTO.getSellerId()));
        Agent agent = agentRepository.findById(commercialBuildingDTO.getAgentId())
                .orElseThrow(() -> new EntityNotFoundException("Agent not found with ID: " + commercialBuildingDTO.getAgentId()));
        commercialBuildingMapper.updateEntityFromDTO(commercialBuildingDTO, existingEntity);
        existingEntity.setSeller(seller);
        existingEntity.setAgent(agent);
        CommercialBuilding updatedEntity = commercialBuildingRepository.save(existingEntity);
        return commercialBuildingMapper.toDTO(updatedEntity);
    }


    @Transactional
    public void deleteCommercialBuilding(String propertyCode) {
        CommercialBuilding commercialBuilding = commercialBuildingRepository.findByPropertyCode(propertyCode);
        if (commercialBuilding == null) {
            throw new EntityNotFoundException("Commercial Building not found with PropertyCode: " + propertyCode);
        }
        commercialBuildingRepository.delete(commercialBuilding);
    }
}
