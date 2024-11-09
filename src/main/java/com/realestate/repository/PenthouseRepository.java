package com.realestate.repository;

import com.realestate.entity.property.urban.residential.Penthouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PenthouseRepository extends
        JpaRepository<Penthouse, Long>,
        JpaSpecificationExecutor<Penthouse> {

    Penthouse findByPropertyCode(String propertyCode);
}