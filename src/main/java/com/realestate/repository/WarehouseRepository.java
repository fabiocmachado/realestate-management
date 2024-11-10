package com.realestate.repository;

import com.realestate.entity.property.urban.comercial.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long>, JpaSpecificationExecutor<Warehouse> {
    Warehouse findByPropertyCode(String propertyCode);
}
