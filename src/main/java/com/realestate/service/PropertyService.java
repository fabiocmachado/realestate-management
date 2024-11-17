package com.realestate.service;

import com.realestate.dto.*;
import com.realestate.entity.property.Property;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    public Optional<PropertyDTO> getPropertyByCode(String propertyCode) {
        return propertyRepository.getPropertyByPropertyCode(propertyCode)
                .map(this::mapToDTO);
    }

    public PropertyDTO getPropertyDTOByCode(String propertyCode, Class<? extends PropertyDTO> propertyClass) {
        Optional<Property> propertyOptional = propertyRepository.getPropertyByPropertyCode(propertyCode);

        if (!propertyOptional.isPresent()) {
            throw new ResourceNotFoundException("Propriedade não encontrada com o código: " + propertyCode);
        }

        Property property = propertyOptional.get();

        if (propertyClass == ApartmentDTO.class) {
            return mapToApartmentDTO(property);
        } else if (propertyClass == HouseDTO.class) {
            return mapToHouseDTO(property);
        } else if (propertyClass == TownhouseDTO.class) {
            return mapToTownhouseDTO(property);
        } else if (propertyClass == UrbanLandDTO.class) {
            return mapToUrbanLandDTO(property);
        } else if (propertyClass == PenthouseDTO.class) {
            return mapToPenthouseDTO(property);
        } else {
            throw new ResourceNotFoundException("Tipo de imóvel não encontrado");
        }
    }

    private ApartmentDTO mapToApartmentDTO(Property property) {
        return new ApartmentDTO();
    }

    private HouseDTO mapToHouseDTO(Property property) {
        return new HouseDTO();
    }

    private TownhouseDTO mapToTownhouseDTO(Property property) {
        return new TownhouseDTO();
    }

    private UrbanLandDTO mapToUrbanLandDTO(Property property) {
        return new UrbanLandDTO();
    }

    private PenthouseDTO mapToPenthouseDTO(Property property) {
        return new PenthouseDTO();
    }

    public Page<PropertyDTO> getAllProperties(Pageable pageable) {
        return propertyRepository.findAll(pageable).map(this::mapToDTO);
    }

    private PropertyDTO mapToDTO(Property property) {
        return PropertyDTO.builder()
                .propertyCode(property.getPropertyCode())
                .price(property.getPrice())
                .address(property.getAddress())
                .description(property.getDescription())
                .propertyCategory(property.getClass().getSimpleName()) // 'Apartment', 'Townhouse', etc.
                .build();
    }
}
