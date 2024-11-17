package com.realestate.service;

import com.realestate.dto.PenthouseDTO;
import com.realestate.entity.person.Agent;
import com.realestate.entity.person.Seller;
import com.realestate.entity.property.urban.residential.Penthouse;
import com.realestate.mapper.PenthouseMapper;
import com.realestate.repository.AgentRepository;
import com.realestate.repository.PenthouseRepository;
import com.realestate.repository.SellerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PenthouseService {

    private final PenthouseRepository penthouseRepository;
    private final SellerRepository sellerRepository;
    private final AgentRepository agentRepository;
    private final PenthouseMapper penthouseMapper;

    @Transactional
    public PenthouseDTO createPenthouse(PenthouseDTO penthouseDTO) {
        Seller seller = sellerRepository.findById(penthouseDTO.getSellerId()).orElseThrow(() ->
                new EntityNotFoundException("Seller not found with ID: " + penthouseDTO.getSellerId()));
        Agent agent = agentRepository.findById(penthouseDTO.getAgentId()).orElseThrow(() ->
                new EntityNotFoundException("Agent not found with ID: " + penthouseDTO.getAgentId()));
        Penthouse penthouse = penthouseMapper.toEntity(penthouseDTO, seller, agent);
        penthouse.setSeller(seller);
        penthouse.setAgent(agent);
        Penthouse savedPenthouse = penthouseRepository.save(penthouse);

        return penthouseMapper.toDto(savedPenthouse);
    }

    @Transactional(readOnly = true)
    public PenthouseDTO getPenthouseByPropertyCode(String propertyCode) {
        Penthouse penthouse = penthouseRepository.findByPropertyCode(propertyCode);
        if (penthouse == null) {
            throw new EntityNotFoundException("Penthouse not found with code: " + propertyCode);
        }
        return penthouseMapper.toDto(penthouse);
    }

    @Transactional(readOnly = true)
    public Page<PenthouseDTO> getAllPenthouses(Pageable pageable) {
        return penthouseRepository.findAll(pageable)
                .map(penthouseMapper::toDto);
    }

    @Transactional
    public PenthouseDTO updatePenthouse(String propertyCode, PenthouseDTO penthouseDTO) {
        Penthouse existingPenthouse = penthouseRepository.findByPropertyCode(propertyCode);
        if (existingPenthouse == null) {
            throw new EntityNotFoundException("Penthouse not found with code: " + propertyCode);
        }
        Seller seller = sellerRepository.findById(penthouseDTO.getSellerId()).orElseThrow(() ->
                new EntityNotFoundException("Seller not found with ID: " + penthouseDTO.getSellerId()));
        Agent agent = agentRepository.findById(penthouseDTO.getAgentId()).orElseThrow(() ->
                new EntityNotFoundException("Agent not found with ID: " + penthouseDTO.getAgentId()));
        penthouseMapper.updateEntity(existingPenthouse, penthouseDTO, seller, agent);
        Penthouse updatedPenthouse = penthouseRepository.save(existingPenthouse);
        return penthouseMapper.toDto(updatedPenthouse);
    }

    @Transactional
    public void deletePenthouse(String propertyCode) {
        Penthouse penthouse = penthouseRepository.findByPropertyCode(propertyCode);
        if (penthouse == null) {
            throw new EntityNotFoundException("Penthouse not found with code: " + propertyCode);
        }
        penthouseRepository.delete(penthouse);
    }
}
