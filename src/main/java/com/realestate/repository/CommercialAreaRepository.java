package com.realestate.repository;

import com.realestate.entity.property.urban.comercial.CommercialArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CommercialAreaRepository extends JpaRepository<CommercialArea, Long>, JpaSpecificationExecutor<CommercialArea> {

    CommercialArea findByPropertyCode (String propertyCode);
}
