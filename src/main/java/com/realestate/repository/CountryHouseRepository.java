package com.realestate.repository;

import com.realestate.entity.property.rural.CountryHouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryHouseRepository extends JpaRepository<CountryHouse, Long>, JpaSpecificationExecutor<CountryHouse> {
    CountryHouse findByPropertyCode(String propertyCode);
}
