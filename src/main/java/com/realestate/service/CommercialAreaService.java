package com.realestate.service;

import com.realestate.dto.CommercialAreaDTO;
import com.realestate.entity.person.Agent;
import com.realestate.entity.person.Seller;
import com.realestate.entity.property.urban.comercial.CommercialArea;
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
    private final CommercialAreaRepository commercialAreaRepository;
    private final AgentRepository agentRepository;

    @Transactional
    public CommercialAreaDTO createCommercialArea(CommercialAreaDTO commercialAreaDTO) {
        CommercialArea commercialArea = convertToEntity(commercialAreaDTO);
        CommercialArea savedCommercialArea = commercialAreaRepository.save(commercialArea);
        return convertToDTO(savedCommercialArea);
    }

    @Transactional(readOnly = true)
    public CommercialAreaDTO getCommercialAreaByCode(String propertyCode) {
        CommercialArea commercialArea = commercialAreaRepository.findByPropertyCode(propertyCode);
        if (commercialArea == null) {
            throw new EntityNotFoundException("Commercial Area not found with code: " + propertyCode);
        }
        return convertToDTO(commercialArea);
    }

    @Transactional(readOnly = true)
    public Page<CommercialAreaDTO> getAllCommercialAreas(Pageable pageable) {
        return commercialAreaRepository.findAll(pageable)
                .map(this::convertToDTO);
    }

    @Transactional
    public CommercialAreaDTO updateCommercialArea(String propertyCode, CommercialAreaDTO commercialAreaDTO) {
        CommercialArea existingCommercialArea = commercialAreaRepository.findByPropertyCode(propertyCode);
        if (existingCommercialArea == null) {
            throw new EntityNotFoundException("Commercial Area not found with code: " + propertyCode);
        }
        updateEntityFromDTO(existingCommercialArea, commercialAreaDTO);
        CommercialArea updatedCommercialArea = commercialAreaRepository.save(existingCommercialArea);
        return convertToDTO(updatedCommercialArea);
    }

    @Transactional
    public void deleteCommercialArea(String propertyCode) {
        CommercialArea commercialArea = commercialAreaRepository.findByPropertyCode(propertyCode);
        if (commercialArea == null) {
            throw new EntityNotFoundException("Commercial Area not found with code: " + propertyCode);
        }
        commercialAreaRepository.delete(commercialArea);
    }

    private CommercialArea convertToEntity(CommercialAreaDTO dto) {
        Seller seller = sellerRepository.findById(dto.getSellerId())
                .orElseThrow(() -> new EntityNotFoundException("Seller not found with ID: " + dto.getSellerId()));

        Agent agent = dto.getAgentId() != null
                ? agentRepository.findById(dto.getAgentId())
                .orElseThrow(() -> new EntityNotFoundException("Agent not found with ID: " + dto.getAgentId()))
                : null;

        return CommercialArea.builder()
                .price(dto.getPrice())
                .address(dto.getAddress())
                .nameOfBuilding(dto.getNameOfBuilding())
                .totalArea(dto.getTotalArea())
                .privateArea(dto.getPrivateArea())
                .usableArea(dto.getUsableArea())
                .description(dto.getDescription())
                .condominiumFee(dto.getCondominiumFee())
                .seller(seller)
                .status(dto.getStatus())
                .prospectedBy(agent)
                .hasSurveillanceCameras(dto.getHasSurveillanceCameras())
                .hasAirConditioning(dto.getHasAirConditioning())
                .floorType(dto.getFloorType())
                .isInhabited(dto.getIsInhabited())
                .isRented(dto.getIsRented())
                .rentalValue(dto.getRentalValue())
                .yearsOfConstruction(dto.getYearsOfConstruction())
                .offices(dto.getOffices())
                .suites(dto.getSuites())
                .visitingTime(dto.getVisitingTime())
                .mezzanine(dto.getMezzanine())
                .hasKitchen(dto.getHasKitchen())
                .garageSpaces(dto.getGarageSpaces())
                .garagesInRow(dto.getGaragesInRow())
                .hasSolarEnergy(dto.getHasSolarEnergy())
                .keyAvailable(dto.getKeyAvailable())
                .hasWall(dto.getHasWall())
                .hasAsphalt(dto.getHasAsphalt())
                .build();
    }

    private CommercialAreaDTO convertToDTO(CommercialArea entity) {
        return CommercialAreaDTO.builder()
                .id(entity.getId())
                .propertyCode(entity.getPropertyCode())
                .price(entity.getPrice())
                .address(entity.getAddress())
                .nameOfBuilding(entity.getNameOfBuilding())
                .totalArea(entity.getTotalArea())
                .privateArea(entity.getPrivateArea())
                .usableArea(entity.getUsableArea())
                .description(entity.getDescription())
                .condominiumFee(entity.getCondominiumFee())
                .sellerId(entity.getSeller().getId())
                .agentId(entity.getProspectedBy() != null ? entity.getProspectedBy().getId() : null)
                .status(entity.getStatus())
                .hasSurveillanceCameras(entity.getHasSurveillanceCameras())
                .hasAirConditioning(entity.getHasAirConditioning())
                .floorType(entity.getFloorType())
                .isInhabited(entity.getIsInhabited())
                .isRented(entity.getIsRented())
                .rentalValue(entity.getRentalValue())
                .yearsOfConstruction(entity.getYearsOfConstruction())
                .offices(entity.getOffices())
                .suites(entity.getSuites())
                .visitingTime(entity.getVisitingTime())
                .mezzanine(entity.getMezzanine())
                .hasKitchen(entity.getHasKitchen())
                .garageSpaces(entity.getGarageSpaces())
                .garagesInRow(entity.getGaragesInRow())
                .hasSolarEnergy(entity.getHasSolarEnergy())
                .keyAvailable(entity.getKeyAvailable())
                .hasWall(entity.getHasWall())
                .hasAsphalt(entity.getHasAsphalt())
                .build();
    }

    private void updateEntityFromDTO(CommercialArea entity, CommercialAreaDTO dto) {
        entity.setPrice(dto.getPrice());
        entity.setAddress(dto.getAddress());
        entity.setNameOfBuilding(dto.getNameOfBuilding());
        entity.setTotalArea(dto.getTotalArea());
        entity.setPrivateArea(dto.getPrivateArea());
        entity.setUsableArea(dto.getUsableArea());
        entity.setDescription(dto.getDescription());
        entity.setCondominiumFee(dto.getCondominiumFee());
        entity.setHasSurveillanceCameras(dto.getHasSurveillanceCameras());
        entity.setHasAirConditioning(dto.getHasAirConditioning());
        entity.setFloorType(dto.getFloorType());
        entity.setIsInhabited(dto.getIsInhabited());
        entity.setIsRented(dto.getIsRented());
        entity.setRentalValue(dto.getRentalValue());
        entity.setYearsOfConstruction(dto.getYearsOfConstruction());
        entity.setOffices(dto.getOffices());
        entity.setSuites(dto.getSuites());
        entity.setVisitingTime(dto.getVisitingTime());
        entity.setMezzanine(dto.getMezzanine());
        entity.setHasKitchen(dto.getHasKitchen());
        entity.setGarageSpaces(dto.getGarageSpaces());
        entity.setGaragesInRow(dto.getGaragesInRow());
        entity.setHasSolarEnergy(dto.getHasSolarEnergy());
        entity.setKeyAvailable(dto.getKeyAvailable());
        entity.setHasWall(dto.getHasWall());
        entity.setHasAsphalt(dto.getHasAsphalt());

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