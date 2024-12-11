package com.realestate.repository;

import com.realestate.entity.property.rural.Rural;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RuralRepository extends JpaRepository<Rural, Long> {

    Optional<Rural> findByPropertyCode(String propertyCode);

}
