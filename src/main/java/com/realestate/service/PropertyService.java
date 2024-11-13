package com.realestate.service;

import com.realestate.entity.property.Property;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.repository.PropertyRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    public Property getProperty(Long id) {
        Optional<Property> propertyOptional = propertyRepository.findById(id);
        if (propertyOptional.isPresent()) {
            Property property = propertyOptional.get();
            Hibernate.initialize(property.getSeller());
            Hibernate.initialize(property.getProspectedBy());
            return property;
        } else {
            throw new ResourceNotFoundException("Property not found");
        }
    }

    public List<Property> getAllProperties() {
        List<Property> properties = propertyRepository.findAll(Sort.by(Sort.Order.desc("id")));
        for (Property property : properties) {
            Hibernate.initialize(property.getSeller());
            Hibernate.initialize(property.getProspectedBy());
        }
        return properties;
    }
}
