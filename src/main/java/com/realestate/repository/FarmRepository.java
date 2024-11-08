package com.realestate.repository;

import com.realestate.entity.property.rural.Farm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FarmRepository extends JpaRepository<Farm, Long> {
    List<Farm> findByCity(String city);
    List<Farm> findByState(String state);
    List<Farm> findByTotalAreaGreaterThanEqual(Float minimumArea);

    Farm findByPropertyCode(String propertyCode);
}
