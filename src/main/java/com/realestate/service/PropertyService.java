package com.realestate.service;

import com.realestate.dto.PropertyDTO;
import com.realestate.entity.property.Property;
import com.realestate.enums.PropertyStatus;
import com.realestate.repository.PropertyRepository;
import com.realestate.exception.InvalidCategoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    public Page<PropertyDTO> getProperties(String status, String category, Pageable pageable) {
        PropertyStatus propertyStatus = parseStatus(status);

        if (category != null && !isValidCategory(category)) {
            throw new InvalidCategoryException("Categoria inválida: " + category);
        }

        Page<Property> properties;

        if (propertyStatus != null && category != null) {
            properties = propertyRepository.findByStatusAndPropertyCategory(propertyStatus, category, pageable);
        } else if (propertyStatus != null) {
            properties = propertyRepository.findByStatus(propertyStatus, pageable);
        } else if (category != null) {
            properties = propertyRepository.findByPropertyCategory(category, pageable);
        } else {
            properties = propertyRepository.findAll(pageable);
        }

        return properties.map(this::convertToDTO);
    }

    private PropertyStatus parseStatus(String status) {
        if (status == null) {
            return null;
        }
        try {
            return PropertyStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Status inválido: " + status);
        }
    }

    private boolean isValidCategory(String category) {
        return category.equalsIgnoreCase("HOUSE") ||
                category.equalsIgnoreCase("APARTMENT") ||
                category.equalsIgnoreCase("PENTHOUSE") ||
                category.equalsIgnoreCase("TOWNHOUSE") ||
                category.equalsIgnoreCase("WAREHOUSE") ||
                category.equalsIgnoreCase("COMMERCIAL_BUILDING") ||
                category.equalsIgnoreCase("FARM") ||
                category.equalsIgnoreCase("COUNTRY_HOUSE") ||
                category.equalsIgnoreCase("URBAN_LAND") ||
                category.equalsIgnoreCase("COMMERCIAL_AREA") ||
                category.equalsIgnoreCase("COMMERCIAL_ROOM");
    }

    private PropertyDTO convertToDTO(Property property) {
        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setId(property.getId());
        propertyDTO.setPropertyCode(property.getPropertyCode());
        propertyDTO.setPrice(property.getPrice());
        propertyDTO.setDescription(property.getDescription());
        propertyDTO.setStatus(property.getStatus());
        propertyDTO.setSellerId(property.getSeller().getId());
        propertyDTO.setAgentId(property.getAgent().getId());
        propertyDTO.setPropertyCategory(property.getPropertyCategory());
        propertyDTO.setStreet(property.getStreet());
        propertyDTO.setBlock(property.getBlock());
        propertyDTO.setLot(property.getLot());
        propertyDTO.setNumber(property.getNumber());
        propertyDTO.setComplement(property.getComplement());
        propertyDTO.setCity(property.getCity());
        propertyDTO.setState(property.getState());
        return propertyDTO;
    }
}
