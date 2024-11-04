package com.realestate.repository;

import com.realestate.entity.property.urban.residential.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ApartmentRepository extends
        JpaRepository<Apartment, Long>,
        JpaSpecificationExecutor<Apartment> {

      Apartment findByPropertyCode(String propertyCode);
}