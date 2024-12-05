package com.realestate.repository;

import com.realestate.entity.property.rural.CountryHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryHouseRepository extends JpaRepository<CountryHouse, Long>, JpaSpecificationExecutor<CountryHouse> {
    Optional<CountryHouse> findByPropertyCode(String propertyCode);
}
