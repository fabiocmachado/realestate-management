package com.realestate.service;

import com.realestate.dto.FarmDTO;
import com.realestate.dto.SellerDTO;
import com.realestate.dto.AgentDTO;
import com.realestate.entity.person.Seller;
import com.realestate.entity.person.Agent;
import com.realestate.entity.property.rural.Farm;
import com.realestate.mapper.FarmMapper;
import com.realestate.repository.FarmRepository;
import com.realestate.repository.SellerRepository;
import com.realestate.repository.AgentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FarmService {

    private final FarmRepository farmRepository;
    private final SellerRepository sellerRepository;
    private final AgentRepository agentRepository;
    private final FarmMapper farmMapper;

    @Transactional
    public FarmDTO createFarm(FarmDTO farmDTO) {
        Farm farm = farmMapper.toEntity(farmDTO, findSeller(farmDTO.getSellerId()), findAgent(farmDTO.getAgentId()));
        Farm savedFarm = farmRepository.save(farm);
        return farmMapper.toDTO(savedFarm);
    }

    @Transactional(readOnly = true)
    public FarmDTO getFarmByCode(String propertyCode) {
        Farm farm = farmRepository.findByPropertyCode(propertyCode)
                .orElseThrow(() -> new EntityNotFoundException("Farm not found with code: " + propertyCode));
        FarmDTO farmDTO = farmMapper.toDTO(farm);
        if (farm.getSeller() != null) {
            farmDTO.setSellerId(farm.getSeller().getId());
        }
        if (farm.getAgent() != null) {
            farmDTO.setAgentId(farm.getAgent().getId());
        }

        return farmDTO;
    }

    @Transactional(readOnly = true)
    public Page<FarmDTO> getAllFarms(Pageable pageable) {
        return farmRepository.findAll(pageable)
                .map(farmMapper::toDTO);
    }

    @Transactional
    public FarmDTO updateFarm(String propertyCode, FarmDTO farmDTO) {
        Farm existingFarm = farmRepository.findByPropertyCode(propertyCode)
                .orElseThrow(() -> new EntityNotFoundException("Farm not found with code: " + propertyCode));

        Seller seller = findSeller(farmDTO.getSellerId());
        Agent agent = findAgent(farmDTO.getAgentId());
        Farm farm = farmMapper.updateEntityFromDTO(farmDTO, existingFarm, seller, agent);
        farmRepository.save(farm);

        return farmMapper.toDTO(farm);
    }

    @Transactional
    public void deleteFarm(String propertyCode) {
        Farm farm = farmRepository.findByPropertyCode(propertyCode)
                .orElseThrow(() -> new EntityNotFoundException("Farm not found with code: " + propertyCode));
        farmRepository.delete(farm);
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
