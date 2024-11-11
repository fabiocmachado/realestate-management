package com.realestate.service;

import com.realestate.dto.AreaMeasurementDTO;
import com.realestate.dto.CountryHouseDTO;
import com.realestate.entity.person.Agent;
import com.realestate.entity.person.Seller;
import com.realestate.entity.property.rural.CountryHouse;
import com.realestate.repository.AgentRepository;
import com.realestate.repository.CountryHouseRepository;
import com.realestate.repository.SellerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CountryHouseService {

    private final CountryHouseRepository countryHouseRepository;
    private final SellerRepository sellerRepository;
    private final AgentRepository agentRepository;

    @Transactional
    public CountryHouseDTO createCountryHouse(CountryHouseDTO countryHouseDTO) {
        CountryHouse countryHouse = convertToEntity(countryHouseDTO);
        CountryHouse savedCountryHouse = countryHouseRepository.save(countryHouse);
        return convertToDTO(savedCountryHouse);
    }

    @Transactional(readOnly = true)
    public CountryHouseDTO getCountryHouseByCode(String propertyCode) {
        CountryHouse countryHouse = countryHouseRepository.findByPropertyCode(propertyCode);
        if (countryHouse == null) {
            throw new EntityNotFoundException("CountryHouse not found with code: " + propertyCode);
        }
        return convertToDTO(countryHouse);
    }

    @Transactional(readOnly = true)
    public Page<CountryHouseDTO> getAllCountryHouses(Pageable pageable) {
        return countryHouseRepository.findAll(pageable)
                .map(this::convertToDTO);
    }

    @Transactional
    public CountryHouseDTO updateCountryHouse(String propertyCode, CountryHouseDTO countryHouseDTO) {
        CountryHouse existingCountryHouse = countryHouseRepository.findByPropertyCode(propertyCode);
        if (existingCountryHouse == null) {
            throw new EntityNotFoundException("CountryHouse not found with code: " + propertyCode);
        }
        updateEntityFromDTO(existingCountryHouse, countryHouseDTO);
        CountryHouse updatedCountryHouse = countryHouseRepository.save(existingCountryHouse);
        return convertToDTO(updatedCountryHouse);
    }

    @Transactional
    public void deleteCountryHouse(String propertyCode) {
        CountryHouse countryHouse = countryHouseRepository.findByPropertyCode(propertyCode);
        if (countryHouse == null) {
            throw new EntityNotFoundException("CountryHouse not found with code: " + propertyCode);
        }
        countryHouseRepository.delete(countryHouse);
    }

    private CountryHouse convertToEntity(CountryHouseDTO dto) {
        Seller seller = sellerRepository.findById(dto.getSellerId())
                .orElseThrow(() -> new EntityNotFoundException("Seller not found with ID: " + dto.getSellerId()));

        Agent agent = dto.getAgentId() != null
                ? agentRepository.findById(dto.getAgentId())
                .orElseThrow(() -> new EntityNotFoundException("Agent not found with ID: " + dto.getAgentId()))
                : null;

        return CountryHouse.builder()
                .name(dto.getName())
                .city(dto.getCity())
                .state(dto.getState())
                .typeOfSoil(dto.getTypeOfSoil())
                .predominantPasture(dto.getPredominantPasture())
                .otherPastures(dto.getOtherPastures())
                .pastureConservation(dto.getPastureConservation())
                .smoothWireFence(dto.getSmoothWireFence())
                .topography(dto.getTopography())
                .rocks(dto.getRocks())
                .rivers(dto.getRivers())
                .dams(dto.getDams())
                .mainHouse(dto.getMainHouse())
                .staffHouse(dto.getStaffHouse())
                .energy(dto.getEnergy())
                .orchard(dto.getOrchard())
                .warehouse(dto.getWarehouse())
                .accommodation(dto.getAccommodation())
                .pens(dto.getPens())
                .landingStrip(dto.getLandingStrip())
                .herdSupport(dto.getHerdSupport())
                .distanceOfGyn(dto.getDistanceOfGyn())
                .distanceOfCity(dto.getDistanceOfCity())
                .distanceDirtRoad(dto.getDistanceDirtRoad())
                .formedArea(dto.getFormedArea().toEntity())
                .pastures(dto.getPastures())
                .seller(seller)
                .prospectedBy(agent)
                .address(dto.getAddress())
                .status(dto.getStatus())
                .price(dto.getPrice())
                .legalReserveArea(dto.getLegalReserveArea().toEntity())
                .totalArea(dto.getTotalArea().toEntity())
                .description(dto.getDescription())
                .build();
    }


    private CountryHouseDTO convertToDTO(CountryHouse entity) {
        return CountryHouseDTO.builder()
                .id(entity.getId())
                .propertyCode(entity.getPropertyCode())
                .name(entity.getName())
                .city(entity.getCity())
                .state(entity.getState())
                .typeOfSoil(entity.getTypeOfSoil())
                .predominantPasture(entity.getPredominantPasture())
                .otherPastures(entity.getOtherPastures())
                .pastureConservation(entity.getPastureConservation())
                .smoothWireFence(entity.getSmoothWireFence())
                .topography(entity.getTopography())
                .rocks(entity.getRocks())
                .rivers(entity.getRivers())
                .dams(entity.getDams())
                .mainHouse(entity.getMainHouse())
                .staffHouse(entity.getStaffHouse())
                .energy(entity.getEnergy())
                .orchard(entity.getOrchard())
                .warehouse(entity.getWarehouse())
                .accommodation(entity.getAccommodation())
                .pens(entity.getPens())
                .landingStrip(entity.getLandingStrip())
                .herdSupport(entity.getHerdSupport())
                .distanceOfGyn(entity.getDistanceOfGyn())
                .distanceOfCity(entity.getDistanceOfCity())
                .distanceDirtRoad(entity.getDistanceDirtRoad())
                .formedArea(AreaMeasurementDTO.fromEntity(entity.getFormedArea()))
                .pastures(entity.getPastures())
                .sellerId(entity.getSeller().getId())
                .agentId(entity.getProspectedBy() != null ? entity.getProspectedBy().getId() : null)
                .status(entity.getStatus())
                .address(entity.getAddress())
                .price(entity.getPrice())
                .legalReserveArea(AreaMeasurementDTO.fromEntity(entity.getLegalReserveArea()))
                .totalArea(AreaMeasurementDTO.fromEntity(entity.getTotalArea()))
                .description(entity.getDescription())
                .build();
    }

    private void updateEntityFromDTO(CountryHouse entity, CountryHouseDTO dto) {
        entity.setName(dto.getName());
        entity.setCity(dto.getCity());
        entity.setState(dto.getState());
        entity.setTypeOfSoil(dto.getTypeOfSoil());
        entity.setPredominantPasture(dto.getPredominantPasture());
        entity.setOtherPastures(dto.getOtherPastures());
        entity.setPastureConservation(dto.getPastureConservation());
        entity.setSmoothWireFence(dto.getSmoothWireFence());
        entity.setTopography(dto.getTopography());
        entity.setRocks(dto.getRocks());
        entity.setRivers(dto.getRivers());
        entity.setDams(dto.getDams());
        entity.setMainHouse(dto.getMainHouse());
        entity.setStaffHouse(dto.getStaffHouse());
        entity.setEnergy(dto.getEnergy());
        entity.setOrchard(dto.getOrchard());
        entity.setWarehouse(dto.getWarehouse());
        entity.setAccommodation(dto.getAccommodation());
        entity.setPens(dto.getPens());
        entity.setLandingStrip(dto.getLandingStrip());
        entity.setHerdSupport(dto.getHerdSupport());
        entity.setDistanceOfGyn(dto.getDistanceOfGyn());
        entity.setDistanceOfCity(dto.getDistanceOfCity());
        entity.setDistanceDirtRoad(dto.getDistanceDirtRoad());
        entity.setFormedArea(dto.getFormedArea().toEntity());
        entity.setPastures(dto.getPastures());
        entity.setAddress(dto.getAddress());
        entity.setStatus(dto.getStatus());
        entity.setPrice(dto.getPrice());
        entity.setTotalArea(dto.toEntity().getTotalArea());
        entity.setLegalReserveArea(dto.toEntity().getLegalReserveArea());
        entity.setDescription(dto.getDescription());

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
