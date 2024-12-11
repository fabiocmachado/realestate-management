package com.realestate.service;

import com.realestate.dto.HouseDTO;
import com.realestate.entity.person.Agent;
import com.realestate.entity.person.Seller;
import com.realestate.entity.property.urban.residential.House;
import com.realestate.mapper.HouseMapper;
import com.realestate.repository.AgentRepository;
import com.realestate.repository.HouseRepository;
import com.realestate.repository.SellerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HouseService {

    private final SellerRepository sellerRepository;
    private final HouseRepository houseRepository;
    private final AgentRepository agentRepository;
    private final HouseMapper houseMapper;

    @Transactional
    public HouseDTO createHouse(HouseDTO houseDTO) {
        Seller seller = sellerRepository.findById(houseDTO.getSellerId())
                .orElseThrow(() -> new EntityNotFoundException("Seller not found with ID: " + houseDTO.getSellerId()));

        Agent agent = houseDTO.getAgentId() != null
                ? agentRepository.findById(houseDTO.getAgentId())
                .orElseThrow(() -> new EntityNotFoundException("Agent not found with ID: " + houseDTO.getAgentId()))
                : null;

        House house = houseMapper.toEntity(houseDTO);
        House savedHouse = houseRepository.save(house);
        return houseMapper.toDTO(savedHouse);
    }

    @Transactional(readOnly = true)
    public HouseDTO getHouseByCode(String propertyCode) {
        House house = houseRepository.findByPropertyCode(propertyCode);
        if (house == null) {
            throw new EntityNotFoundException("House not found with code: " + propertyCode);
        }
        return houseMapper.toDTO(house);
    }

    @Transactional(readOnly = true)
    public Page<HouseDTO> getAllHouses(Pageable pageable) {
        return houseRepository.findAll(pageable).map(houseMapper::toDTO);
    }

    @Transactional
    public HouseDTO updateHouse(String propertyCode, HouseDTO houseDTO) {
        House existingHouse = houseRepository.findByPropertyCode(propertyCode);
        if (existingHouse == null) {
            throw new EntityNotFoundException("House not found with code: " + propertyCode);
        }

        Seller seller = sellerRepository.findById(houseDTO.getSellerId())
                .orElseThrow(() -> new EntityNotFoundException("Seller not found with ID: " + houseDTO.getSellerId()));

        Agent agent = houseDTO.getAgentId() != null
                ? agentRepository.findById(houseDTO.getAgentId())
                .orElseThrow(() -> new EntityNotFoundException("Agent not found with ID: " + houseDTO.getAgentId()))
                : null;

        houseMapper.updateEntityFromDTO(houseDTO, existingHouse);
        House updatedHouse = houseRepository.save(existingHouse);
        return houseMapper.toDTO(updatedHouse);
    }

    @Transactional
    public void deleteHouse(String propertyCode) {
        House house = houseRepository.findByPropertyCode(propertyCode);
        if (house == null) {
            throw new EntityNotFoundException("House not found with code: " + propertyCode);
        }
        houseRepository.delete(house);
    }
}
