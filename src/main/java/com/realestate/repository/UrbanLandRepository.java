package com.realestate.repository;

import com.realestate.entity.property.urban.residential.UrbanLand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrbanLandRepository extends JpaRepository<UrbanLand, Long> {

    Optional<UrbanLand> findByPropertyCode(String propertyCode);
}