package com.realestate.service;

import com.realestate.dto.ApartmentDTO;
import com.realestate.entity.person.Agent;
import com.realestate.entity.person.Seller;
import com.realestate.entity.property.urban.residential.Apartment;
import com.realestate.repository.AgentRepository;
import com.realestate.repository.ApartmentRepository;
import com.realestate.repository.SellerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApartmentService {

    private final SellerRepository sellerRepository;
    private final ApartmentRepository apartmentRepository;
    private final AgentRepository agentRepository;

    @Transactional
    public ApartmentDTO createApartment(ApartmentDTO apartmentDTO) {
        Apartment apartment = convertToEntity(apartmentDTO);
        Apartment savedApartment = apartmentRepository.save(apartment);
        return convertToDTO(savedApartment);
    }

    @Transactional(readOnly = true)
    public ApartmentDTO getApartmentByCode(String propertyCode) {
        Apartment apartment = apartmentRepository.findByPropertyCode(propertyCode);
        if (apartment == null) {
            throw new EntityNotFoundException("Apartment not found with code: " + propertyCode);
        }
        return convertToDTO(apartment);
    }

    @Transactional(readOnly = true)
    public Page<ApartmentDTO> getAllApartments(Pageable pageable) {
        return apartmentRepository.findAll(pageable)
                .map(this::convertToDTO);
    }

    @Transactional
    public ApartmentDTO updateApartment(String propertyCode, ApartmentDTO apartmentDTO) {
        Apartment existingApartment = apartmentRepository.findByPropertyCode(propertyCode);
        if (existingApartment == null) {
            throw new EntityNotFoundException("Apartment not found with code: " + propertyCode);
        }
        updateEntityFromDTO(existingApartment, apartmentDTO);
        Apartment updatedApartment = apartmentRepository.save(existingApartment);
        return convertToDTO(updatedApartment);
    }

    @Transactional
    public void deleteApartment(String propertyCode) {
        Apartment apartment = apartmentRepository.findByPropertyCode(propertyCode);
        if (apartment == null) {
            throw new EntityNotFoundException("Apartment not found with code: " + propertyCode);
        }
        apartmentRepository.delete(apartment);
    }

    private Apartment convertToEntity(ApartmentDTO dto) {
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

    private ApartmentDTO convertToDTO(Apartment entity) {
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
                .agentId(entity.getProspectedBy().getId())
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

    private void updateEntityFromDTO(Apartment entity, ApartmentDTO dto) {
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
        entity.setPantry(dto.getPantry());
        entity.setHasLaundry(dto.getHasLaundry());
        entity.setSauna(dto.getSauna());
        entity.setHasCabinets(dto.getHasCabinets());
        entity.setHasSurveillanceCameras(dto.getHasSurveillanceCameras());
        entity.setHasAirConditioning(dto.getHasAirConditioning());
        entity.setFloorType(dto.getFloorType());
        entity.setIsInhabited(dto.getIsInhabited());
        entity.setIsRented(dto.getIsRented());
        entity.setHasBalcony(dto.getHasBalcony());
        entity.setYearsOfConstruction(dto.getYearsOfConstruction());
        entity.setHasSwimmingPool(dto.getHasSwimmingPool());
        entity.setHasBarbecueGrill(dto.getHasBarbecueGrill());
        entity.setToilet(dto.getToilet());
        entity.setSuites(dto.getSuites());
        entity.setBars(dto.getBars());
        entity.setEmployeeRoom(dto.getEmployeeRoom());
        entity.setEmployeeBathroom(dto.getEmployeeBathroom());
        entity.setGaragesInRow(dto.getGaragesInRow());
        entity.setNumberOfBlocks(dto.getNumberOfBlocks());
        entity.setTotalOfApartments(dto.getTotalOfApartments());
        entity.setPartyHall(dto.getPartyHall());
        entity.setGamesRoom(dto.getGamesRoom());
        entity.setPlayground(dto.getPlayground());
        entity.setToyArea(dto.getToyArea());
        entity.setSportsCourt(dto.getSportsCourt());
        entity.setHasElectronicGate(dto.getHasElectronicGate());
        entity.setElectronicDoorman(dto.getElectronicDoorman());
        entity.setIntercom(dto.getIntercom());
        entity.setGourmetBalcony(dto.getGourmetBalcony());
        entity.setElevator(dto.getElevator());
        entity.setCompartment(dto.getCompartment());
        entity.setVisitingTime(dto.getVisitingTime());
        entity.setExclusiveSwimmingPool(dto.getExclusiveSwimmingPool());
        entity.setExclusiveSauna(dto.getExclusiveSauna());
        entity.setMezzanine(dto.getMezzanine());

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