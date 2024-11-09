package com.realestate.repository;

import com.realestate.entity.property.urban.residential.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {
    House findByPropertyCode(String propertyCode);
}
