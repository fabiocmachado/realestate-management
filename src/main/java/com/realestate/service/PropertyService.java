package com.realestate.service;

import com.realestate.entity.property.Property;
import com.realestate.enums.PropertyStatus;
import com.realestate.exception.InvalidCategoryException;
import com.realestate.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    public Page<Property> getProperties(String status, String category, Pageable pageable) {
        PropertyStatus propertyStatus = parseStatus(status);
        if (category != null && !isValidCategory(category)) {
            throw new InvalidCategoryException("Categoria inválida: " + category);
        }

        PageRequest pageRequest = createPageRequest(pageable);
        if (propertyStatus != null && category != null) {
            return propertyRepository.findByStatusAndPropertyCategory(propertyStatus, category, pageRequest);
        } else if (propertyStatus != null) {
            return propertyRepository.findByStatus(propertyStatus, pageRequest);
        } else if (category != null) {
            return propertyRepository.findByPropertyCategory(category, pageRequest);
        } else {
            return propertyRepository.findAll(pageRequest);
        }
    }

    public Property getPropertyByCode(String propertyCode) {
        return propertyRepository.findPropertyByPropertyCode(propertyCode)
                .orElseThrow(() -> new RuntimeException("Imóvel não encontrado para o código: " + propertyCode));
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
}
