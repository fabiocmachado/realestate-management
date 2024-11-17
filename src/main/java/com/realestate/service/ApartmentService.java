package com.realestate.service;

import com.realestate.dto.ApartmentDTO;
import com.realestate.entity.person.Agent;
import com.realestate.entity.person.Seller;
import com.realestate.entity.property.urban.residential.Apartment;
import com.realestate.mapper.ApartmentMapper;
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
    private final ApartmentMapper apartmentMapper;

    @Transactional
    public ApartmentDTO createApartment(ApartmentDTO apartmentDTO) {
        Apartment apartment = apartmentMapper.toEntity(apartmentDTO, sellerRepository.findById(apartmentDTO.getSellerId()).orElseThrow(), agentRepository.findById(apartmentDTO.getAgentId()).orElseThrow());
        Apartment savedApartment = apartmentRepository.save(apartment);
        return apartmentMapper.toDto(savedApartment);
    }

    @Transactional(readOnly = true)
    public ApartmentDTO getApartmentByCode(String propertyCode) {
        Apartment apartment = apartmentRepository.findByPropertyCode(propertyCode);
        if (apartment == null) {
            throw new EntityNotFoundException("Apartment not found with code: " + propertyCode);
        }
        return apartmentMapper.toDto(apartment);
    }

    @Transactional(readOnly = true)
    public Page<ApartmentDTO> getAllApartments(Pageable pageable) {
        return apartmentRepository.findAll(pageable)
                .map(apartmentMapper::toDto);
    }

    @Transactional
    public ApartmentDTO updateApartment(String propertyCode, ApartmentDTO apartmentDTO) {
        Apartment existingApartment = apartmentRepository.findByPropertyCode(propertyCode);
        if (existingApartment == null) {
            throw new EntityNotFoundException("Apartment not found with code: " + propertyCode);
        }
        apartmentMapper.updateEntity(existingApartment, apartmentDTO, sellerRepository.findById(apartmentDTO.getSellerId()).orElseThrow(), agentRepository.findById(apartmentDTO.getAgentId()).orElseThrow());
        Apartment updatedApartment = apartmentRepository.save(existingApartment);
        return apartmentMapper.toDto(updatedApartment);
    }

    @Transactional
    public void deleteApartment(String propertyCode) {
        Apartment apartment = apartmentRepository.findByPropertyCode(propertyCode);
        if (apartment == null) {
            throw new EntityNotFoundException("Apartment not found with code: " + propertyCode);
        }
        apartmentRepository.delete(apartment);
    }
}
