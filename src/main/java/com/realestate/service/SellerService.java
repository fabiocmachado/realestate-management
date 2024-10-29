package com.realestate.service;

import com.realestate.dto.SellerDTO;
import com.realestate.entity.person.Seller;
import com.realestate.repository.SellerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SellerService {

    private final SellerRepository sellerRepository;

    @Autowired
    public SellerService(SellerRepository sellerRepository) {
        this.sellerRepository = sellerRepository;
    }

    @Transactional(readOnly = true)
    public List<SellerDTO> findAll() {
        return sellerRepository.findAll().stream()
                .map(SellerDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SellerDTO findById(Long id) {
        return sellerRepository.findById(id)
                .map(SellerDTO::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Vendedor não encontrado com ID: " + id));
    }

    @Transactional
    public SellerDTO create(SellerDTO sellerDTO) {
        if (sellerRepository.existsByCpf(sellerDTO.getCpf())) {
            throw new IllegalArgumentException("CPF já cadastrado");
        }
        Seller seller = sellerDTO.toEntity();
        seller = sellerRepository.save(seller);
        return SellerDTO.fromEntity(seller);
    }

    @Transactional
    public SellerDTO update(Long id, SellerDTO sellerDTO) {
        if (!sellerRepository.existsById(id)) {
            throw new EntityNotFoundException("Vendedor não encontrado com ID: " + id);
        }

        Seller existingSeller = sellerRepository.findByCpf(sellerDTO.getCpf())
                .orElse(null);

        if (existingSeller != null && !existingSeller.getId().equals(id)) {
            throw new IllegalArgumentException("CPF já cadastrado para outro vendedor");
        }

        Seller seller = sellerDTO.toEntity();
        seller.setId(id);
        seller = sellerRepository.save(seller);
        return SellerDTO.fromEntity(seller);
    }

    @Transactional
    public void delete(Long id) {
        if (!sellerRepository.existsById(id)) {
            throw new EntityNotFoundException("Vendedor não encontrado com ID: " + id);
        }
        sellerRepository.deleteById(id);
    }
}