package com.realestate.service;

import com.realestate.dto.TownhouseDTO;
import com.realestate.entity.person.Agent;
import com.realestate.entity.person.Seller;
import com.realestate.entity.property.urban.residential.Townhouse;
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

    @Transactional
    public TownhouseDTO createTownhouse(TownhouseDTO townhouseDTO) {
        Townhouse townhouse = convertToEntity(townhouseDTO);
        Townhouse savedTownhouse = townhouseRepository.save(townhouse);
        return convertToDTO(savedTownhouse);
    }

    @Transactional(readOnly = true)
    public TownhouseDTO getTownhouseByCode(String propertyCode) {
        Townhouse townhouse = (Townhouse) townhouseRepository.findByPropertyCode(propertyCode);
        if (townhouse == null) {
            throw new EntityNotFoundException("Townhouse not found with code: " + propertyCode);
        }
        return convertToDTO(townhouse);
    }

    @Transactional(readOnly = true)
    public Page<TownhouseDTO> getAllTownhouses(Pageable pageable) {
        return townhouseRepository.findAll(pageable).map(this::convertToDTO);
    }

    @Transactional
    public TownhouseDTO updateTownhouse(String propertyCode, TownhouseDTO townhouseDTO) {
        Townhouse existingTownhouse = (Townhouse) townhouseRepository.findByPropertyCode(propertyCode);
        if (existingTownhouse == null) {
            throw new EntityNotFoundException("Townhouse not found with code: " + propertyCode);
        }
        updateEntityFromDTO(existingTownhouse, townhouseDTO);
        Townhouse updatedTownhouse = townhouseRepository.save(existingTownhouse);
        return convertToDTO(updatedTownhouse);
    }

    @Transactional
    public void deleteTownhouse(String propertyCode) {
        Townhouse townhouse = (Townhouse) townhouseRepository.findByPropertyCode(propertyCode);
        if (townhouse == null) {
            throw new EntityNotFoundException("Townhouse not found with code: " + propertyCode);
        }
        townhouseRepository.delete(townhouse);
    }

    private Townhouse convertToEntity(TownhouseDTO dto) {
        Seller seller = sellerRepository.findById(dto.getSellerId())
                .orElseThrow(() -> new EntityNotFoundException("Seller not found with ID: " + dto.getSellerId()));

        Agent agent = dto.getAgentId() != null
                ? agentRepository.findById(dto.getAgentId())
                .orElseThrow(() -> new EntityNotFoundException("Agent not found with ID: " + dto.getAgentId()))
                : null;

        return Townhouse.builder()
                .price(dto.getPrice())
                .address(dto.getAddress())
                .bedrooms(dto.getBedrooms())
                .bathrooms(dto.getBathrooms())
                .suites(dto.getSuites())
                .livingRoom(dto.getLivingRoom())
                .offices(dto.getOffices())
                .hasLaundry(dto.getHasLaundry())
                .sauna(dto.getSauna())
                .hasCabinets(dto.getHasCabinets())
                .hasElectronicGate(dto.getHasElectronicGate())
                .hasSurveillanceCameras(dto.getHasSurveillanceCameras())
                .hasGarden(dto.getHasGarden())
                .hasAirConditioning(dto.getHasAirConditioning())
                .hasKennel(dto.getHasKennel())
                .floorType(dto.getFloorType())
                .isInhabited(dto.getIsInhabited())
                .isRented(dto.getIsRented())
                .hasBalcony(dto.getHasBalcony())
                .yearsOfConstruction(dto.getYearsOfConstruction())
                .hasYard(dto.getHasYard())
                .hasSwimmingPool(dto.getHasSwimmingPool())
                .hasBarbecueGrill(dto.getHasBarbecueGrill())
                .toilet(dto.getToilet())
                .hasSolarEnergy(dto.getHasSolarEnergy())
                .condominiumFee(dto.getCondominiumFee())
                .nameOfGatedCommunity(dto.getNameOfGatedCommunity())
                .usableArea(dto.getUsableArea())
                .privateArea(dto.getPrivateArea())
                .totalArea(dto.getTotalArea())
                .seller(seller)
                .status(dto.getStatus())
                .prospectedBy(agent)
                .pantry(dto.getPantry())
                .build();
    }

    private TownhouseDTO convertToDTO(Townhouse entity) {
        return TownhouseDTO.builder()
                .id(entity.getId())
                .propertyCode(entity.getPropertyCode())
                .price(entity.getPrice())
                .address(entity.getAddress())
                .suites(entity.getSuites())
                .livingRoom(entity.getLivingRoom())
                .offices(entity.getOffices())
                .hasLaundry(entity.getHasLaundry())
                .sauna(entity.getSauna())
                .hasCabinets(entity.getHasCabinets())
                .hasElectronicGate(entity.getHasElectronicGate())
                .hasSurveillanceCameras(entity.getHasSurveillanceCameras())
                .hasGarden(entity.getHasGarden())
                .hasAirConditioning(entity.getHasAirConditioning())
                .hasKennel(entity.getHasKennel())
                .floorType(entity.getFloorType())
                .isInhabited(entity.getIsInhabited())
                .isRented(entity.getIsRented())
                .hasBalcony(entity.getHasBalcony())
                .yearsOfConstruction(entity.getYearsOfConstruction())
                .hasYard(entity.getHasYard())
                .hasSwimmingPool(entity.getHasSwimmingPool())
                .hasBarbecueGrill(entity.getHasBarbecueGrill())
                .toilet(entity.getToilet())
                .hasSolarEnergy(entity.getHasSolarEnergy())
                .condominiumFee(entity.getCondominiumFee())
                .nameOfGatedCommunity(entity.getNameOfGatedCommunity())
                .usableArea(entity.getUsableArea())
                .privateArea(entity.getPrivateArea())
                .totalArea(entity.getTotalArea())
                .sellerId(entity.getSeller().getId())
                .agentId(entity.getProspectedBy() != null ? entity.getProspectedBy().getId() : null)
                .status(entity.getStatus())
                .bedrooms(entity.getBedrooms())
                .bathrooms(entity.getBathrooms())
                .pantry(entity.getPantry())
                .build();
    }

    private void updateEntityFromDTO(Townhouse entity, TownhouseDTO dto) {
        entity.setPrice(dto.getPrice());
        entity.setAddress(dto.getAddress());
        entity.setBedrooms(dto.getBedrooms());
        entity.setBathrooms(dto.getBathrooms());
        entity.setSuites(dto.getSuites());
        entity.setLivingRoom(dto.getLivingRoom());
        entity.setOffices(dto.getOffices());
        entity.setHasLaundry(dto.getHasLaundry());
        entity.setSauna(dto.getSauna());
        entity.setHasCabinets(dto.getHasCabinets());
        entity.setHasElectronicGate(dto.getHasElectronicGate());
        entity.setHasSurveillanceCameras(dto.getHasSurveillanceCameras());
        entity.setHasGarden(dto.getHasGarden());
        entity.setHasAirConditioning(dto.getHasAirConditioning());
        entity.setHasKennel(dto.getHasKennel());
        entity.setFloorType(dto.getFloorType());
        entity.setIsInhabited(dto.getIsInhabited());
        entity.setIsRented(dto.getIsRented());
        entity.setHasBalcony(dto.getHasBalcony());
        entity.setYearsOfConstruction(dto.getYearsOfConstruction());
        entity.setHasYard(dto.getHasYard());
        entity.setHasSwimmingPool(dto.getHasSwimmingPool());
        entity.setHasBarbecueGrill(dto.getHasBarbecueGrill());
        entity.setToilet(dto.getToilet());
        entity.setHasSolarEnergy(dto.getHasSolarEnergy());
        entity.setCondominiumFee(dto.getCondominiumFee());
        entity.setNameOfGatedCommunity(dto.getNameOfGatedCommunity());
        entity.setUsableArea(dto.getUsableArea());
        entity.setPrivateArea(dto.getPrivateArea());
        entity.setTotalArea(dto.getTotalArea());
        entity.setStatus(dto.getStatus());
        entity.setPantry(dto.getPantry());
        Seller seller = sellerRepository.findById(dto.getSellerId())
                .orElseThrow(() -> new EntityNotFoundException("Seller not found with ID: " + dto.getSellerId()));
        if (dto.getAgentId() != null) {
            Agent agent = agentRepository.findById(dto.getAgentId())
                    .orElseThrow(() -> new EntityNotFoundException("Agent not found with ID: " + dto.getAgentId()));
            entity.setProspectedBy(agent);
        } else {
            entity.setProspectedBy(null);
        }
    }
}
