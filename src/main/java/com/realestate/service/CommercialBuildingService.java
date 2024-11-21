package com.realestate.service;

import com.realestate.dto.CommercialBuildingDTO;
import com.realestate.entity.person.Agent;
import com.realestate.entity.person.Seller;
import com.realestate.entity.property.urban.comercial.CommercialBuilding;
import com.realestate.mapper.CommercialBuildingMapper;
import com.realestate.repository.CommercialBuildingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommercialBuildingService {

    private final CommercialBuildingRepository commercialBuildingRepository;
    private final CommercialBuildingMapper commercialBuildingMapper;
    private final SellerService sellerService;
    private final AgentService agentService;

    @Transactional
    public CommercialBuildingDTO createCommercialBuilding(CommercialBuildingDTO commercialBuildingDTO) {
        Seller seller = sellerService.findById(commercialBuildingDTO.getSellerId()).toEntity();
        Agent agent = agentService.findAgentById(commercialBuildingDTO.getAgentId())
                .orElseThrow(() -> new EntityNotFoundException("Agente não encontrado com ID: " + commercialBuildingDTO.getAgentId()))
                .toEntity();
        CommercialBuilding commercialBuilding = commercialBuildingMapper.toEntity(commercialBuildingDTO, seller, agent);
        CommercialBuilding savedEntity = commercialBuildingRepository.save(commercialBuilding);
        return commercialBuildingMapper.toDTO(savedEntity);
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

        Seller seller = sellerService.findById(commercialBuildingDTO.getSellerId()).toEntity();
        Agent agent = agentService.findAgentById(commercialBuildingDTO.getAgentId())
                .orElseThrow(() -> new EntityNotFoundException("Agente não encontrado com ID: " + commercialBuildingDTO.getAgentId()))
                .toEntity();
        commercialBuildingMapper.updateEntityFromDTO(existingEntity, commercialBuildingDTO, seller, agent);
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
