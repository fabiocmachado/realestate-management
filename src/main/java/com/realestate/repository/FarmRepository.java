package com.realestate.repository;

import com.realestate.entity.property.rural.Farm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FarmRepository extends JpaRepository<Farm, Long> {
    List<Farm> findByCity(String city);
    List<Farm> findByState(String state);
    List<Farm> findByTotalAreaGreaterThanEqual(Float minimumArea);

    Optional<Farm> findByPropertyCode(String propertyCode);
}
