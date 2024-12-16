package com.realestate.repository;

import com.realestate.entity.property.Property;
import com.realestate.enums.PropertyStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long>, JpaSpecificationExecutor<Property> {
    Page<Property> findAll(Pageable pageable);
    Optional<Property> getPropertyByPropertyCode(String propertyCode);
    Optional<Property> findTopByOrderByIdDesc();
    boolean existsByPropertyCode(String propertyCode);
    List<Property> findByIdIn(List<Long> ids);
    Page<Property> findByStatus(PropertyStatus status, Pageable pageable);
    Page<Property> findByPropertyCategory(String propertyCategory, Pageable pageable);
    Page<Property> findByStatusAndPropertyCategory(PropertyStatus status, String category, Pageable pageable);}


