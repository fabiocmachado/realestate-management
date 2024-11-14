package com.realestate.service;

import com.realestate.dto.PropertyDTO;
import com.realestate.entity.property.Property;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    public PropertyDTO getPropertyDTOByCode(String propertyCode) {
        Property property = propertyRepository.getPropertyByPropertyCode(propertyCode)
                .orElseThrow(() -> new ResourceNotFoundException("Property not found with code: " + propertyCode));
        return mapToDTO(property);
    }

    public Page<PropertyDTO> getAllProperties(Pageable pageable) {
        Page<Property> propertyPage = propertyRepository.findAll(pageable);
        return propertyPage.map(this::mapToDTO);
    }

    private PropertyDTO mapToDTO(Property property) {
        return PropertyDTO.builder()
                .propertyCode(property.getPropertyCode())
                .price(property.getPrice())
                .address(property.getAddress())
                .description(property.getDescription())
                .propertyCategory(property.getClass().getSimpleName())  // 'Apartment', 'Townhouse', etc.
                .build();
    }
}
