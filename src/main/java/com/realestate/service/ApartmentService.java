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
                .totalArea(dto.getTotalArea())
                .description(dto.getDescription())
                .bedrooms(dto.getBedrooms())
                .bathrooms(dto.getBathrooms())
                .garageSpaces(dto.getGarageSpaces())
                .apartmentNumber(dto.getApartmentNumber())
                .floor(dto.getFloor())
                .livingRoom(dto.getLivingRoom())
                .condominiumFee(dto.getCondominiumFee())
                .seller(seller)
                .status(dto.getStatus())
                .prospectedBy(agent)
                .build();
    }


    private ApartmentDTO convertToDTO(Apartment entity) {
        return ApartmentDTO.builder()
                .id(entity.getId())
                .propertyCode(entity.getPropertyCode())
                .price(entity.getPrice())
                .address(entity.getAddress())
                .totalArea(entity.getTotalArea())
                .description(entity.getDescription())
                .bedrooms(entity.getBedrooms())
                .bathrooms(entity.getBathrooms())
                .garageSpaces(entity.getGarageSpaces())
                .apartmentNumber(entity.getApartmentNumber())
                .floor(entity.getFloor())
                .livingRoom(entity.getLivingRoom())
                .condominiumFee(entity.getCondominiumFee())
                .sellerId(entity.getSeller().getId())
                .agentId(entity.getProspectedBy().getId())
                .status(entity.getStatus())
                .build();
    }

    private void updateEntityFromDTO(Apartment entity, ApartmentDTO dto) {
        entity.setPrice(dto.getPrice());
        entity.setAddress(dto.getAddress());
        entity.setTotalArea(dto.getTotalArea());
        entity.setDescription(dto.getDescription());
        entity.setBedrooms(dto.getBedrooms());
        entity.setBathrooms(dto.getBathrooms());
        entity.setGarageSpaces(dto.getGarageSpaces());
        entity.setApartmentNumber(dto.getApartmentNumber());
        entity.setFloor(dto.getFloor());
        entity.setLivingRoom(dto.getLivingRoom());
        entity.setCondominiumFee(dto.getCondominiumFee());
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