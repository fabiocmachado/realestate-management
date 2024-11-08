package com.realestate.service;

import com.realestate.dto.HouseDTO;
import com.realestate.entity.person.Agent;
import com.realestate.entity.person.Seller;
import com.realestate.entity.property.urban.residential.House;
import com.realestate.repository.AgentRepository;
import com.realestate.repository.HouseRepository;
import com.realestate.repository.SellerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HouseService {

    private final SellerRepository sellerRepository;
    private final HouseRepository houseRepository;
    private final AgentRepository agentRepository;

    @Transactional
    public HouseDTO createHouse(HouseDTO houseDTO) {
        House house = convertToEntity(houseDTO);
        House savedHouse = houseRepository.save(house);
        return convertToDTO(savedHouse);
    }

    @Transactional(readOnly = true)
    public HouseDTO getHouseByCode(String propertyCode) {
        House house = houseRepository.findByPropertyCode(propertyCode);
        if (house == null) {
            throw new EntityNotFoundException("House not found with code: " + propertyCode);
        }
        return convertToDTO(house);
    }

    @Transactional(readOnly = true)
    public Page<HouseDTO> getAllHouses(Pageable pageable) {
        return houseRepository.findAll(pageable).map(this::convertToDTO);
    }

    @Transactional
    public HouseDTO updateHouse(String propertyCode, HouseDTO houseDTO) {
        House existingHouse = houseRepository.findByPropertyCode(propertyCode);
        if (existingHouse == null) {
            throw new EntityNotFoundException("House not found with code: " + propertyCode);
        }
        updateEntityFromDTO(existingHouse, houseDTO);
        House updatedHouse = houseRepository.save(existingHouse);
        return convertToDTO(updatedHouse);
    }

    @Transactional
    public void deleteHouse(String propertyCode) {
        House house = houseRepository.findByPropertyCode(propertyCode);
        if (house == null) {
            throw new EntityNotFoundException("House not found with code: " + propertyCode);
        }
        houseRepository.delete(house);
    }

    private House convertToEntity(HouseDTO dto) {
        Seller seller = sellerRepository.findById(dto.getSellerId())
                .orElseThrow(() -> new EntityNotFoundException("Seller not found with ID: " + dto.getSellerId()));

        Agent agent = dto.getAgentId() != null
                ? agentRepository.findById(dto.getAgentId())
                .orElseThrow(() -> new EntityNotFoundException("Agent not found with ID: " + dto.getAgentId()))
                : null;

        return House.builder()
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
                .build();
    }

    private HouseDTO convertToDTO(House entity) {
        return HouseDTO.builder()
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
                .build();
    }

    private void updateEntityFromDTO(House entity, HouseDTO dto) {
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
    }
}
