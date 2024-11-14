package com.realestate.repository;

import com.realestate.entity.property.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> , JpaSpecificationExecutor<Property> {
    Page<Property> findAll(Pageable pageable);;
    Optional<Property> getPropertyByPropertyCode(String propertyCode);}

