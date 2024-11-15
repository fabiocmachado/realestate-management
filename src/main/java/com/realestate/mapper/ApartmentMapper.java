package com.realestate.mapper;

import com.realestate.dto.ApartmentDTO;
import com.realestate.entity.person.Agent;
import com.realestate.entity.person.Seller;
import com.realestate.entity.property.urban.residential.Apartment;
import com.realestate.repository.AgentRepository;
import com.realestate.repository.SellerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface ApartmentMapper {

    public default Apartment toEntity(ApartmentDTO dto, SellerRepository sellerRepository, AgentRepository agentRepository) {
        Seller seller = sellerRepository.findById(dto.getSellerId())
                .orElseThrow(() -> new EntityNotFoundException("Seller not found with ID: " + dto.getSellerId()));

        Agent agent = dto.getAgentId() != null
                ? agentRepository.findById(dto.getAgentId())
                .orElseThrow(() -> new EntityNotFoundException("Agent not found with ID: " + dto.getAgentId()))
                : null;

        return Apartment.builder()
                .price(dto.getPrice())
                .address(dto.getAddress())
                .nameOfBuilding(dto.getNameOfBuilding())
                .totalArea(dto.getTotalArea())
                .privateArea(dto.getPrivateArea())
                .usableArea(dto.getUsableArea())
                .description(dto.getDescription())
                .bedrooms(dto.getBedrooms())
                .bathrooms(dto.getBathrooms())
                .garageSpaces(dto.getGarageSpaces())
                .apartmentNumber(dto.getApartmentNumber())
                .floor(dto.getFloors())
                .livingRoom(dto.getLivingRoom())
                .offices(dto.getOffices())
                .condominiumFee(dto.getCondominiumFee())
                .seller(seller)
                .status(dto.getStatus())
                .prospectedBy(agent)
                .pantry(dto.getPantry())
                .hasLaundry(dto.getHasLaundry())
                .sauna(dto.getSauna())
                .hasCabinets(dto.getHasCabinets())
                .hasSurveillanceCameras(dto.getHasSurveillanceCameras())
                .hasAirConditioning(dto.getHasAirConditioning())
                .floorType(dto.getFloorType())
                .isInhabited(dto.getIsInhabited())
                .isRented(dto.getIsRented())
                .hasBalcony(dto.getHasBalcony())
                .yearsOfConstruction(dto.getYearsOfConstruction())
                .hasSwimmingPool(dto.getHasSwimmingPool())
                .hasBarbecueGrill(dto.getHasBarbecueGrill())
                .toilet(dto.getToilet())
                .suites(dto.getSuites())
                .bars(dto.getBars())
                .employeeRoom(dto.getEmployeeRoom())
                .employeeBathroom(dto.getEmployeeBathroom())
                .garagesInRow(dto.getGaragesInRow())
                .numberOfBlocks(dto.getNumberOfBlocks())
                .totalOfApartments(dto.getTotalOfApartments())
                .partyHall(dto.getPartyHall())
                .gamesRoom(dto.getGamesRoom())
                .playground(dto.getPlayground())
                .toyArea(dto.getToyArea())
                .sportsCourt(dto.getSportsCourt())
                .hasElectronicGate(dto.getHasElectronicGate())
                .electronicDoorman(dto.getElectronicDoorman())
                .intercom(dto.getIntercom())
                .gourmetBalcony(dto.getGourmetBalcony())
                .elevator(dto.getElevator())
                .compartment(dto.getCompartment())
                .visitingTime(dto.getVisitingTime())
                .exclusiveSwimmingPool(dto.getExclusiveSwimmingPool())
                .exclusiveSauna(dto.getExclusiveSauna())
                .mezzanine(dto.getMezzanine())
                .build();
    }

    public default ApartmentDTO toDTO(Apartment entity) {
        return ApartmentDTO.builder()
                .id(entity.getId())
                .propertyCode(entity.getPropertyCode())
                .price(entity.getPrice())
                .address(entity.getAddress())
                .nameOfBuilding(entity.getNameOfBuilding())
                .totalArea(entity.getTotalArea())
                .privateArea(entity.getPrivateArea())
                .usableArea(entity.getUsableArea())
                .description(entity.getDescription())
                .bedrooms(entity.getBedrooms())
                .bathrooms(entity.getBathrooms())
                .garageSpaces(entity.getGarageSpaces())
                .apartmentNumber(entity.getApartmentNumber())
                .floors(entity.getFloor())
                .livingRoom(entity.getLivingRoom())
                .offices(entity.getOffices())
                .condominiumFee(entity.getCondominiumFee())
                .sellerId(entity.getSeller().getId())
                .agentId(entity.getProspectedBy() != null ? entity.getProspectedBy().getId() : null)
                .status(entity.getStatus())
                .pantry(entity.getPantry())
                .hasLaundry(entity.getHasLaundry())
                .sauna(entity.getSauna())
                .hasCabinets(entity.getHasCabinets())
                .hasSurveillanceCameras(entity.getHasSurveillanceCameras())
                .hasAirConditioning(entity.getHasAirConditioning())
                .floorType(entity.getFloorType())
                .isInhabited(entity.getIsInhabited())
                .isRented(entity.getIsRented())
                .hasBalcony(entity.getHasBalcony())
                .yearsOfConstruction(entity.getYearsOfConstruction())
                .hasSwimmingPool(entity.getHasSwimmingPool())
                .hasBarbecueGrill(entity.getHasBarbecueGrill())
                .toilet(entity.getToilet())
                .suites(entity.getSuites())
                .bars(entity.getBars())
                .employeeRoom(entity.getEmployeeRoom())
                .employeeBathroom(entity.getEmployeeBathroom())
                .garagesInRow(entity.getGaragesInRow())
                .numberOfBlocks(entity.getNumberOfBlocks())
                .totalOfApartments(entity.getTotalOfApartments())
                .partyHall(entity.getPartyHall())
                .gamesRoom(entity.getGamesRoom())
                .playground(entity.getPlayground())
                .toyArea(entity.getToyArea())
                .sportsCourt(entity.getSportsCourt())
                .hasElectronicGate(entity.getHasElectronicGate())
                .electronicDoorman(entity.getElectronicDoorman())
                .intercom(entity.getIntercom())
                .gourmetBalcony(entity.getGourmetBalcony())
                .elevator(entity.getElevator())
                .compartment(entity.getCompartment())
                .visitingTime(entity.getVisitingTime())
                .exclusiveSwimmingPool(entity.getExclusiveSwimmingPool())
                .exclusiveSauna(entity.getExclusiveSauna())
                .mezzanine(entity.getMezzanine())
                .build();
    }

    public default void updateEntityFromDTO(Apartment entity, ApartmentDTO dto, SellerRepository sellerRepository, AgentRepository agentRepository) {
        entity.setPrice(dto.getPrice());
        entity.setAddress(dto.getAddress());
        entity.setNameOfBuilding(dto.getNameOfBuilding());
        entity.setTotalArea(dto.getTotalArea());
        entity.setPrivateArea(dto.getPrivateArea());
        entity.setUsableArea(dto.getUsableArea());
        entity.setDescription(dto.getDescription());
        entity.setBedrooms(dto.getBedrooms());
        entity.setBathrooms(dto.getBathrooms());
        entity.setGarageSpaces(dto.getGarageSpaces());
        entity.setApartmentNumber(dto.getApartmentNumber());
        entity.setFloor(dto.getFloors());
        entity.setLivingRoom(dto.getLivingRoom());
        entity.setOffices(dto.getOffices());
        entity.setCondominiumFee(dto.getCondominiumFee());

        Seller seller = sellerRepository.findById(dto.getSellerId())
                .orElseThrow(() -> new EntityNotFoundException("Seller not found with ID: " + dto.getSellerId()));
        entity.setSeller(seller);

        Agent agent = dto.getAgentId() != null
                ? agentRepository.findById(dto.getAgentId())
                .orElseThrow(() -> new EntityNotFoundException("Agent not found with ID: " + dto.getAgentId()))
                : null;
        entity.setProspectedBy(agent);
    }
}
