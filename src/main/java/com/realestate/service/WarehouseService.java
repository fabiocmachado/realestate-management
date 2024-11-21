package com.realestate.service;

import com.realestate.dto.WarehouseDTO;
import com.realestate.entity.person.Agent;
import com.realestate.entity.person.Seller;
import com.realestate.entity.property.urban.comercial.Warehouse;
import com.realestate.mapper.WarehouseMapper;
import com.realestate.repository.AgentRepository;
import com.realestate.repository.WarehouseRepository;
import com.realestate.repository.SellerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final SellerRepository sellerRepository;
    private final WarehouseRepository warehouseRepository;
    private final AgentRepository agentRepository;
    private final WarehouseMapper warehouseMapper;

    @Transactional
    public WarehouseDTO createWarehouse(WarehouseDTO warehouseDTO) {
        Seller seller = sellerRepository.findById(warehouseDTO.getSellerId())
                .orElseThrow(() -> new EntityNotFoundException("Seller not found with ID: " + warehouseDTO.getSellerId()));
        Agent agent = agentRepository.findById(warehouseDTO.getAgentId())
                .orElseThrow(() -> new EntityNotFoundException("Agent not found with ID: " + warehouseDTO.getAgentId()));
        Warehouse warehouse = warehouseMapper.toEntity(warehouseDTO, seller, agent);
        Warehouse savedWarehouse = warehouseRepository.save(warehouse);
        return warehouseMapper.toDTO(savedWarehouse);
    }

    @Transactional(readOnly = true)
    public WarehouseDTO getWarehouseByCode(String propertyCode) {
        Warehouse warehouse = warehouseRepository.findByPropertyCode(propertyCode);
        if (warehouse == null) {
            throw new EntityNotFoundException("Warehouse not found with code: " + propertyCode);
        }
        return warehouseMapper.toDTO(warehouse);
    }

    @Transactional(readOnly = true)
    public Page<WarehouseDTO> getAllWarehouses(Pageable pageable) {
        return warehouseRepository.findAll(pageable)
                .map(warehouseMapper::toDTO);
    }

    @Transactional
    public WarehouseDTO updateWarehouse(String propertyCode, WarehouseDTO warehouseDTO) {
        Warehouse existingWarehouse = warehouseRepository.findByPropertyCode(propertyCode);
        if (existingWarehouse == null) {
            throw new EntityNotFoundException("Warehouse not found with code: " + propertyCode);
        }
        Seller seller = sellerRepository.findById(warehouseDTO.getSellerId())
                .orElseThrow(() -> new EntityNotFoundException("Seller not found with ID: " + warehouseDTO.getSellerId()));
        Agent agent = agentRepository.findById(warehouseDTO.getAgentId())
                .orElseThrow(() -> new EntityNotFoundException("Agent not found with ID: " + warehouseDTO.getAgentId()));

        warehouseMapper.updateEntityFromDTO(existingWarehouse, warehouseDTO, seller, agent);
        Warehouse updatedWarehouse = warehouseRepository.save(existingWarehouse);
        return warehouseMapper.toDTO(updatedWarehouse);
    }

    @Transactional
    public void deleteWarehouse(String propertyCode) {
        Warehouse warehouse = warehouseRepository.findByPropertyCode(propertyCode);
        if (warehouse == null) {
            throw new EntityNotFoundException("Warehouse not found with code: " + propertyCode);
        }
        warehouseRepository.delete(warehouse);
    }
}
