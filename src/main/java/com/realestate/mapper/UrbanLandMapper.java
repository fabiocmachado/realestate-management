package com.realestate.mapper;

import com.realestate.dto.UrbanLandDTO;
import com.realestate.entity.person.Agent;
import com.realestate.entity.person.Seller;
import com.realestate.entity.property.urban.residential.UrbanLand;
import com.realestate.repository.AgentRepository;
import com.realestate.repository.SellerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UrbanLandMapper {

    default UrbanLand toEntity(UrbanLandDTO dto, SellerRepository sellerRepository, AgentRepository agentRepository) {
        Seller seller = sellerRepository.findById(dto.getSellerId())
                .orElseThrow(() -> new EntityNotFoundException("Seller not found with ID: " + dto.getSellerId()));

        Agent agent = dto.getAgentId() != null
                ? agentRepository.findById(dto.getAgentId())
                .orElseThrow(() -> new EntityNotFoundException("Agent not found with ID: " + dto.getAgentId()))
                : null;

        return UrbanLand.builder()
                .price(dto.getPrice())
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

    default UrbanLandDTO toDTO(UrbanLand entity) {
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

    default void updateEntityFromDTO(UrbanLand entity, UrbanLandDTO dto, SellerRepository sellerRepository, AgentRepository agentRepository) {
        entity.setPrice(dto.getPrice());
        entity.setAddress(dto.getAddress());
        entity.setDescription(dto.getDescription());
        entity.setTotalArea(dto.getTotalArea());
        entity.setHasWall(dto.getHasWall());
        entity.setHasAsphalt(dto.getHasAsphalt());
        entity.setKeyAvailable(dto.getKeyAvailable());

        Seller seller = sellerRepository.findById(dto.getSellerId())
                .orElseThrow(() -> new EntityNotFoundException("Seller not found with ID: " + dto.getSellerId()));
        entity.setSeller(seller);

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
