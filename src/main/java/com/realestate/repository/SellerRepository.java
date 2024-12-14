package com.realestate.repository;

import com.realestate.entity.person.Seller;
import com.realestate.entity.property.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
    Optional<Seller> findByCpf(String cpf);
    boolean existsByCpf(String cpf);
    List<Property> findByIdIn(List<Long> ids);
    }
