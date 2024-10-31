package com.realestate.service;

import com.realestate.dto.BuyerDTO;
import com.realestate.entity.person.Buyer;
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

    @Autowired
    public BuyerService(BuyerRepository buyerRepository) {
        this.buyerRepository = buyerRepository;
    }

    @Transactional(readOnly = true)
    public List<BuyerDTO> findAll() {
        return buyerRepository.findAll().stream()
                .map(BuyerDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BuyerDTO findById(Long id) {
        return buyerRepository.findById(id)
                .map(BuyerDTO::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Comprador não encontrado com ID: " + id));
    }

    @Transactional
    public BuyerDTO create(BuyerDTO buyerDTO) {
        if (buyerRepository.existsByCpf(buyerDTO.getCpf())) {
            throw new IllegalArgumentException("CPF já cadastrado");
        }
        Buyer buyer = buyerDTO.toEntity();
        buyer = buyerRepository.save(buyer);
        return BuyerDTO.fromEntity(buyer);
    }

    @Transactional
    public BuyerDTO update(Long id, BuyerDTO buyerDTO) {
        if (!buyerRepository.existsById(id)) {
            throw new EntityNotFoundException("Comprador não encontrado com ID: " + id);
        }

        Buyer existingBuyer = buyerRepository.findByCpf(buyerDTO.getCpf())
                .orElse(null);

        if (existingBuyer != null && !existingBuyer.getId().equals(id)) {
            throw new IllegalArgumentException("CPF já cadastrado para outro comprador");
        }

        Buyer buyer = buyerDTO.toEntity();
        buyer.setId(id);
        buyer = buyerRepository.save(buyer);
        return BuyerDTO.fromEntity(buyer);
    }

    @Transactional
    public void delete(Long id) {
        if (!buyerRepository.existsById(id)) {
            throw new EntityNotFoundException("Comprador não encontrado com ID: " + id);
        }
        buyerRepository.deleteById(id);
    }
}