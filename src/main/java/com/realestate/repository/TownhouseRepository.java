package com.realestate.repository;

import com.realestate.entity.property.urban.residential.House;
import com.realestate.entity.property.urban.residential.Townhouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TownhouseRepository extends JpaRepository<Townhouse, Long> {
    Townhouse findByPropertyCode(String propertyCode);
}
