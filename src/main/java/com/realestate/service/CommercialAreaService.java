package com.realestate.service;

import com.realestate.dto.ApartmentDTO;
import com.realestate.dto.CommercialAreaDTO;
import com.realestate.entity.property.urban.comercial.CommercialArea;
import com.realestate.entity.property.urban.residential.Apartment;
import com.realestate.mapper.CommercialAreaMapper;
import com.realestate.repository.AgentRepository;
import com.realestate.repository.CommercialAreaRepository;
import com.realestate.repository.SellerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommercialAreaService {

    private final SellerRepository sellerRepository;
    private final AgentRepository agentRepository;
    private final CommercialAreaRepository commercialAreaRepository;
    private final CommercialAreaMapper commercialAreaMapper;

    @Transactional
    public CommercialAreaDTO createCommercialArea(CommercialAreaDTO commercialAreaDTO) {
        CommercialArea commercialArea = commercialAreaMapper.toEntity(commercialAreaDTO, sellerRepository.findById(commercialAreaDTO.getSellerId()).orElseThrow(), agentRepository.findById(commercialAreaDTO.getAgentId()).orElseThrow());
        CommercialArea savedEntity = commercialAreaRepository.save(commercialArea);
        return commercialAreaMapper.toDTO(savedEntity);
    }

    @Transactional(readOnly = true)
    public CommercialAreaDTO getCommercialAreaByCode(String propertyCode) {
        CommercialArea commercialArea = commercialAreaRepository.findByPropertyCode(propertyCode);
        if (commercialArea == null) {
            throw new EntityNotFoundException("Commercial Area not found with PropertyCode: " + propertyCode);
        }
        return commercialAreaMapper.toDTO(commercialArea);
    }

    @Transactional(readOnly = true)
    public Page<CommercialAreaDTO> getAllCommercialAreas(Pageable pageable) {
        return commercialAreaRepository.findAll(pageable)
                .map(commercialAreaMapper::toDTO);
    }

    @Transactional
    public CommercialAreaDTO updateCommercialArea(String propertyCode, CommercialAreaDTO commercialAreaDTO) {
        CommercialArea existingEntity = commercialAreaRepository.findByPropertyCode(propertyCode);
        if (existingEntity == null) {
            throw new EntityNotFoundException("Commercial Area not found with PropertyCode: " + propertyCode);
        }

        commercialAreaMapper.updateEntityFromDTO(existingEntity, commercialAreaDTO, null, null); // Passe os parâmetros Seller e Agent conforme necessário
        CommercialArea updatedEntity = commercialAreaRepository.save(existingEntity);
        return commercialAreaMapper.toDTO(updatedEntity);
    }

    @Transactional
    public void deleteCommercialArea(String propertyCode) {
        CommercialArea commercialArea = commercialAreaRepository.findByPropertyCode(propertyCode);
        if (commercialArea == null) {
            throw new EntityNotFoundException("Commercial Area not found with PropertyCode: " + propertyCode);
        }
        commercialAreaRepository.delete(commercialArea);
    }
}
