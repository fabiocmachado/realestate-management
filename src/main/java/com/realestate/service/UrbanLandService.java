package com.realestate.service;

import com.realestate.dto.UrbanLandDTO;
import com.realestate.entity.person.Agent;
import com.realestate.entity.person.Seller;
import com.realestate.entity.property.urban.residential.UrbanLand;
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

    @Transactional
    public UrbanLandDTO createUrbanLand(UrbanLandDTO urbanLandDTO) {
        UrbanLand urbanLand = convertToEntity(urbanLandDTO);
        UrbanLand savedUrbanLand = urbanLandRepository.save(urbanLand);
        return convertToDTO(savedUrbanLand);
    }

    @Transactional(readOnly = true)
    public UrbanLandDTO getUrbanLandByCode(String propertyCode) {
        UrbanLand urbanLand = urbanLandRepository.findByPropertyCode(propertyCode)
                .orElseThrow(() -> new EntityNotFoundException("Urban Land not found with code: " + propertyCode));
        return convertToDTO(urbanLand);
    }

    @Transactional(readOnly = true)
    public Page<UrbanLandDTO> getAllUrbanLands(Pageable pageable) {
        return urbanLandRepository.findAll(pageable)
                .map(this::convertToDTO);
    }

    @Transactional
    public UrbanLandDTO updateUrbanLand(String propertyCode, UrbanLandDTO urbanLandDTO) {
        UrbanLand existingUrbanLand = urbanLandRepository.findByPropertyCode(propertyCode)
                .orElseThrow(() -> new EntityNotFoundException("Urban Land not found with code: " + propertyCode));
        updateEntityFromDTO(existingUrbanLand, urbanLandDTO);
        UrbanLand updatedUrbanLand = urbanLandRepository.save(existingUrbanLand);
        return convertToDTO(updatedUrbanLand);
    }

    @Transactional
    public void deleteUrbanLand(String propertyCode) {
        UrbanLand urbanLand = urbanLandRepository.findByPropertyCode(propertyCode)
                .orElseThrow(() -> new EntityNotFoundException("Urban Land not found with code: " + propertyCode));
        urbanLandRepository.delete(urbanLand);
    }

    private UrbanLand convertToEntity(UrbanLandDTO dto) {
        Seller seller = sellerRepository.findById(dto.getSellerId())
                .orElseThrow(() -> new EntityNotFoundException("Seller not found with ID: " + dto.getSellerId()));

        Agent agent = dto.getAgentId() != null
                ? agentRepository.findById(dto.getAgentId())
                .orElseThrow(() -> new EntityNotFoundException("Agent not found with ID: " + dto.getAgentId()))
                : null;

        return UrbanLand.builder()
                .price((dto.getPrice()))
                .address(dto.getAddress())
                .description(dto.getDescription())
                .totalArea(dto.getTotalArea())
                .hasWall(dto.getHasWall())
                .hasAsphalt(dto.getHasAsphalt())
                .keyAvailable(dto.getKeyAvailable())
                .status(dto.getStatus())
                .seller(seller)
                .prospectedBy(agent)
                .build();
    }

    private UrbanLandDTO convertToDTO(UrbanLand entity) {
        return UrbanLandDTO.builder()
                .id(entity.getId())
                .propertyCode(entity.getPropertyCode())
                .price(entity.getPrice())
                .address(entity.getAddress())
                .description(entity.getDescription())
                .totalArea(entity.getTotalArea())
                .hasWall(entity.getHasWall())
                .hasAsphalt(entity.getHasAsphalt())
                .keyAvailable(entity.getKeyAvailable())
                .sellerId(entity.getSeller().getId())
                .agentId(entity.getProspectedBy() != null ? entity.getProspectedBy().getId() : null)
                .status(entity.getStatus())
                .build();
    }

    private void updateEntityFromDTO(UrbanLand entity, UrbanLandDTO dto) {
        entity.setPrice((dto.getPrice()));
        entity.setAddress(dto.getAddress());
        entity.setDescription(dto.getDescription());
        entity.setTotalArea(dto.getTotalArea());
        entity.setHasWall(dto.getHasWall());
        entity.setHasAsphalt(dto.getHasAsphalt());
        entity.setKeyAvailable(dto.getKeyAvailable());
        Seller seller = sellerRepository.findById(dto.getSellerId())
                .orElseThrow(() -> new EntityNotFoundException("Seller not found with ID: " + dto.getSellerId()));
        if (dto.getAgentId() != null) {
            Agent agent = agentRepository.findById(dto.getAgentId())
                    .orElseThrow(() -> new EntityNotFoundException("Agent not found with ID: " + dto.getAgentId()));
            entity.setProspectedBy(agent);
        } else {
            entity.setProspectedBy(null);
        }
        entity.setStatus(dto.getStatus());
    }
}