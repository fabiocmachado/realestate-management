package com.realestate.service;

import com.realestate.dto.SellerDTO;
import com.realestate.entity.person.Seller;
import com.realestate.entity.property.Property;
import com.realestate.mapper.SellerMapper;
import com.realestate.repository.PropertyRepository;
import com.realestate.repository.SellerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SellerService {

    private final SellerRepository sellerRepository;
    private final SellerMapper sellerMapper;
    private final PropertyRepository propertyRepository;

    public SellerService(SellerRepository sellerRepository,
                         SellerMapper sellerMapper,
                         PropertyRepository propertyRepository) {
        this.sellerRepository = sellerRepository;
        this.sellerMapper = sellerMapper;
        this.propertyRepository = propertyRepository;
    }

    @Transactional(readOnly = true)
    public List<SellerDTO> findAll() {
        return sellerMapper.toDTOList(sellerRepository.findAll());
    }

    @Transactional(readOnly = true)
    public SellerDTO findById(Long id) {
        return sellerRepository.findById(id)
                .map(sellerMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Vendedor não encontrado com ID: " + id));
    }

    @Transactional
    public SellerDTO create(SellerDTO sellerDTO) {
        if (sellerRepository.existsByCpf(sellerDTO.getCpf())) {
            throw new IllegalArgumentException("CPF já cadastrado");
        }
        Seller seller = sellerMapper.toEntity(sellerDTO, propertyRepository);
        return sellerMapper.toDTO(sellerRepository.save(seller));
    }

    @Transactional
    public SellerDTO update(Long id, SellerDTO sellerDTO) {
        // Buscar o vendedor existente
        Seller seller = sellerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vendedor não encontrado com ID: " + id));

        // Validar CPF
        if (sellerRepository.existsByCpf(sellerDTO.getCpf()) && !seller.getCpf().equals(sellerDTO.getCpf())) {
            throw new IllegalArgumentException("CPF já cadastrado para outro vendedor");
        }

        // Atualizar dados básicos do vendedor
        sellerMapper.updateEntityFromDTO(sellerDTO, seller, propertyRepository);

        // Atualizar propriedades corretamente
        if (sellerDTO.getProperties() != null) {
            // Buscar as novas propriedades
            List<Property> newProperties = propertyRepository.findByIdIn(sellerDTO.getProperties());

            // Limpar associações antigas
            if (seller.getProperties() != null) {
                seller.getProperties().forEach(property -> property.setSeller(null));
                seller.getProperties().clear();
            }

            // Estabelecer novas associações
            newProperties.forEach(property -> {
                property.setSeller(seller);
                if (seller.getProperties() == null) {
                    seller.setProperties(new ArrayList<>());
                }
                seller.getProperties().add(property);
            });
        }

        // Salvar e retornar
        return sellerMapper.toDTO(sellerRepository.save(seller));
    }

    @Transactional
    public void delete(Long id) {
        Seller seller = sellerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vendedor não encontrado com ID: " + id));
        sellerRepository.delete(seller);
    }
}
