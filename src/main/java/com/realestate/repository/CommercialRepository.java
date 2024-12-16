package com.realestate.repository;

import com.realestate.entity.property.urban.comercial.Commercial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommercialRepository extends JpaRepository<Commercial, Long> {

    Optional<Commercial> findByPropertyCode(String propertyCode);

}
