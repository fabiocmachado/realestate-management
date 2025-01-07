package com.realestate.service;

import com.realestate.dto.CountryHouseDTO;
import com.realestate.entity.person.Agent;
import com.realestate.entity.person.Seller;
import com.realestate.entity.property.rural.CountryHouse;
import com.realestate.mapper.CountryHouseMapper;
import com.realestate.repository.AgentRepository;
import com.realestate.repository.CountryHouseRepository;
import com.realestate.repository.SellerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CountryHouseService {

    private final CountryHouseRepository countryHouseRepository;
    private final SellerRepository sellerRepository;
    private final AgentRepository agentRepository;
    private final CountryHouseMapper countryHouseMapper;

    @Transactional
    public CountryHouseDTO createCountryHouse(CountryHouseDTO countryHouseDTO) {
        CountryHouse countryHouse = countryHouseMapper.toEntity(countryHouseDTO);
        CountryHouse savedCountryHouse = countryHouseRepository.save(countryHouse);
        return countryHouseMapper.toDTO(savedCountryHouse);
    }

    @Transactional(readOnly = true)
    public CountryHouseDTO getCountryHouseByCode(String propertyCode) {
        CountryHouse countryHouse = countryHouseRepository.findByPropertyCode(propertyCode)
                .orElseThrow(() -> new EntityNotFoundException("Farm not found with code: " + propertyCode));

        CountryHouseDTO countryHouseDTO = countryHouseMapper.toDTO(countryHouse);

        if (countryHouse.getSeller() != null) {
            countryHouseDTO.setSellerId(countryHouse.getSeller().getId());
        }
        if (countryHouse.getAgent() != null) {
            countryHouseDTO.setAgentId(countryHouse.getAgent().getId());
        }

        return countryHouseDTO;
    }


    @Transactional(readOnly = true)
    public Page<CountryHouseDTO> getAllCountryHouses(Pageable pageable) {
        return countryHouseRepository.findAll(pageable)
                .map(countryHouseMapper::toDTO);
    }

    @Transactional
    public CountryHouseDTO updateCountryHouse(String propertyCode, CountryHouseDTO countryHouseDTO) {
        CountryHouse existingCountryHouse = countryHouseRepository.findByPropertyCode(propertyCode)
                .orElseThrow(() -> new EntityNotFoundException("Farm not found with code: " + propertyCode));

        Seller seller = findSeller(countryHouseDTO.getSellerId());
        Agent agent = findAgent(countryHouseDTO.getAgentId());
        CountryHouse countryHouse = countryHouseMapper.updateEntityFromDTO(countryHouseDTO, existingCountryHouse);
        existingCountryHouse.setSeller(seller);
        existingCountryHouse.setAgent(agent);
        countryHouseRepository.save(countryHouse);

        return countryHouseMapper.toDTO(countryHouse);
    }

    @Transactional
    public void deleteCountryHouse(String propertyCode) {
        CountryHouse countryHouse = countryHouseRepository.findByPropertyCode(propertyCode)
                .orElseThrow(() -> new EntityNotFoundException("CountryHouse not found with code: " + propertyCode));
        countryHouseRepository.delete(countryHouse);
    }


    private Seller findSeller(Long sellerId) {
        return sellerRepository.findById(sellerId)
                .orElseThrow(() -> new EntityNotFoundException("Seller not found with ID: " + sellerId));
    }

    private Agent findAgent(Long agentId) {
        return agentRepository.findById(agentId)
                .orElseThrow(() -> new EntityNotFoundException("Agent not found with ID: " + agentId));
    }

}