package com.realestate.repository;

import com.realestate.entity.person.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {
    Optional<Agent> findByCpf(String cpf);
    Optional<Agent> findByLicenseNumber(String licenseNumber);
    boolean existsByCpf(String cpf);
    boolean existsByLicenseNumber(String licenseNumber);

    @Query("SELECT a FROM Agent a WHERE SIZE(a.prospectedProperties) > 0")
    List<Agent> findAllWithProperties();

    @Query("SELECT a FROM Agent a LEFT JOIN FETCH a.prospectedProperties WHERE a.id = :id")
    Optional<Agent> findByIdWithProperties(Long id);
}