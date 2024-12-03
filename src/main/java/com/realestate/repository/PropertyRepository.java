package com.realestate.repository;

import com.realestate.dto.PropertyDTO;
import com.realestate.entity.property.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long>, JpaSpecificationExecutor<Property> {
    Page<Property> findAll(Pageable pageable);
    Optional<Property> findTopByOrderByIdDesc();
    boolean existsByPropertyCode(String propertyCode);
    @Query("SELECT new com.realestate.dto.PropertyDTO(p.propertyCode, p.price, p.address, p.description, p.class.simpleName) " +
            "FROM Property p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<PropertyDTO> findByNameContainingIgnoreCase(String searchTerm);

    @Query("SELECT new com.realestate.dto.PropertyDTO(p.propertyCode, p.price, p.address, p.description, p.class.simpleName) " +
            "FROM Property p")
    List<PropertyDTO> findAllProperties();

    Optional<Property> getPropertyByPropertyCode(String propertyCode);
}


