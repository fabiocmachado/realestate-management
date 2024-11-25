package com.realestate.service;

import com.realestate.dto.CommercialAreaDTO;
import com.realestate.dto.CommercialBuildingDTO;
import com.realestate.entity.person.Agent;
import com.realestate.entity.person.Seller;
import com.realestate.entity.property.urban.comercial.CommercialArea;
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

    private final CommercialBuildingRepository commercialBuildingRepository;
    private final CommercialBuildingMapper commercialBuildingMapper;
    private final SellerRepository sellerRepository;
    private final AgentRepository agentRepository;

    @Transactional
    public CommercialBuildingDTO createCommercialBuilding(CommercialBuildingDTO commercialBuildingDTO) {
        CommercialBuilding commercialBuilding = commercialBuildingMapper.toEntity(commercialBuildingDTO, sellerRepository.findById(commercialBuildingDTO.getSellerId()).orElseThrow(), agentRepository.findById(commercialBuildingDTO.getAgentId()).orElseThrow());
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

        commercialBuildingMapper.updateEntityFromDTO(existingEntity, commercialBuildingDTO,sellerRepository.findById(commercialBuildingDTO.getSellerId()).orElseThrow(), agentRepository.findById(commercialBuildingDTO.getAgentId()).orElseThrow()); // Passe os parâmetros Seller e Agent conforme necessário
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
