package com.realestate.service;

import com.realestate.dto.BuyerDTO;
import com.realestate.entity.person.Agent;
import com.realestate.entity.person.Buyer;
import com.realestate.repository.AgentRepository;
import com.realestate.repository.BuyerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BuyerService {

    private final BuyerRepository buyerRepository;
    private final AgentRepository agentRepository;

    @Autowired
    public BuyerService(BuyerRepository buyerRepository, AgentRepository agentRepository) {
        this.buyerRepository = buyerRepository;
        this.agentRepository = agentRepository;
    }

    @Transactional(readOnly = true)
    public List<BuyerDTO> findAll() {
        return buyerRepository.findAll().stream()
                .map(this::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BuyerDTO findById(Long id) {
        Buyer buyer = buyerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comprador não encontrado com ID: " + id));
        return fromEntity(buyer);
    }

    @Transactional
    public BuyerDTO create(BuyerDTO buyerDTO) {
        if (buyerRepository.existsByCpf(buyerDTO.getCpf())) {
            throw new IllegalArgumentException("CPF já cadastrado");
        }

        Agent responsibleAgent = agentRepository.findById(buyerDTO.getResponsibleAgentId())
                .orElseThrow(() -> new EntityNotFoundException("Agente responsável não encontrado"));

        Buyer buyer = toEntity(buyerDTO, responsibleAgent);
        buyer = buyerRepository.save(buyer);
        return fromEntity(buyer);
    }

    @Transactional
    public BuyerDTO update(Long id, BuyerDTO buyerDTO) {
        Buyer existingBuyer = buyerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comprador não encontrado com ID: " + id));

        Agent responsibleAgent = agentRepository.findById(buyerDTO.getResponsibleAgentId())
                .orElseThrow(() -> new EntityNotFoundException("Agente responsável não encontrado"));

        Buyer updatedBuyer = toEntity(buyerDTO, responsibleAgent);
        updatedBuyer.setId(existingBuyer.getId());
        updatedBuyer = buyerRepository.save(updatedBuyer);
        return fromEntity(updatedBuyer);
    }

    @Transactional
    public void delete(Long id) {
        if (!buyerRepository.existsById(id)) {
            throw new EntityNotFoundException("Comprador não encontrado com ID: " + id);
        }
        buyerRepository.deleteById(id);
    }

    private BuyerDTO fromEntity(Buyer buyer) {
        return BuyerDTO.builder()
                .id(buyer.getId())
                .name(buyer.getName())
                .cpf(buyer.getCpf())
                .rg(buyer.getRg())
                .email(buyer.getEmail())
                .phone(buyer.getPhone())
                .address(buyer.getAddress())
                .responsibleAgentId(buyer.getResponsibleAgent().getId())
                .registrationDate(buyer.getRegistrationDate())
                .firstContactDate(buyer.getFirstContactDate())
                .build();
    }

    private Buyer toEntity(BuyerDTO dto, Agent responsibleAgent) {
        return Buyer.builder()
                .id(dto.getId())
                .name(dto.getName())
                .cpf(dto.getCpf())
                .rg(dto.getRg())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .registrationDate(dto.getRegistrationDate())
                .firstContactDate(dto.getFirstContactDate())
                .responsibleAgent(responsibleAgent)
                .build();
    }
}