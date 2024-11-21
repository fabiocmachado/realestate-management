package com.realestate.repository;

import com.realestate.entity.property.urban.comercial.CommercialBuilding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommercialBuildingRepository extends JpaRepository<CommercialBuilding, Long> {
    CommercialBuilding findByPropertyCode(String propertyCode);
}
