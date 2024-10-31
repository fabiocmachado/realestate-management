package com.realestate.repository;

import com.realestate.entity.person.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BuyerRepository extends JpaRepository<Buyer, Long> {
    Optional<Buyer> findByCpf(String cpf);
    boolean existsByCpf(String cpf);
}