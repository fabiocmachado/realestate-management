package com.realestate.service;

import com.realestate.dto.PropertyDTO;
import com.realestate.entity.property.Property;
import com.realestate.enums.PropertyStatus;
import com.realestate.repository.PropertyRepository;
import com.realestate.exception.InvalidCategoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    public Page<PropertyDTO> getProperties(String status, String category, Pageable pageable) {
        PropertyStatus propertyStatus = parseStatus(status);
        if (category != null && !isValidCategory(category)) {
            throw new InvalidCategoryException("Categoria inválida: " + category);
        }

        PageRequest pageRequest = createPageRequest(pageable);
        Page<Property> properties;

        if (propertyStatus != null && category != null) {
            properties = propertyRepository.findByStatusAndPropertyCategory(propertyStatus, category, pageRequest);
        } else if (propertyStatus != null) {
            properties = propertyRepository.findByStatus(propertyStatus, pageRequest);
        } else if (category != null) {
            properties = propertyRepository.findByPropertyCategory(category, pageRequest);
        } else {
            properties = propertyRepository.findAll(pageRequest);
        }

        return properties.map(this::convertToDTO);
    }

    public PropertyDTO getPropertyByCode(String propertyCode) {
        Optional<Property> property = propertyRepository.findPropertyByPropertyCode(propertyCode);
        if (property.isPresent()) {
            return convertToDTO(property.get());
        } else {
            throw new RuntimeException("Imóvel não encontrado para o código: " + propertyCode);
        }
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

    private PageRequest createPageRequest(Pageable pageable) {
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Order.desc("createdAt")));
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
        propertyDTO.setCreatedAt(property.getCreatedAt());
        return propertyDTO;
    }
}
