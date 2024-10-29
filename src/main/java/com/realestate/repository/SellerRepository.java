package com.realestate.repository;

import com.realestate.entity.person.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
    Optional<Seller> findByCpf(String cpf);
    boolean existsByCpf(String cpf);
}