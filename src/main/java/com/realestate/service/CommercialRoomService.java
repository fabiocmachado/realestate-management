package com.realestate.service;

import com.realestate.dto.CommercialRoomDTO;
import com.realestate.entity.person.Agent;
import com.realestate.entity.person.Seller;
import com.realestate.entity.property.urban.comercial.CommercialRoom;
import com.realestate.mapper.CommercialRoomMapper;
import com.realestate.repository.AgentRepository;
import com.realestate.repository.CommercialRoomRepository;
import com.realestate.repository.SellerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommercialRoomService {

    private final SellerRepository sellerRepository;
    private final AgentRepository agentRepository;
    private final CommercialRoomRepository commercialRoomRepository;
    private final CommercialRoomMapper commercialRoomMapper;

    @Transactional
    public CommercialRoomDTO createCommercialRoom(CommercialRoomDTO commercialRoomDTO) {
        CommercialRoom commercialRoom = commercialRoomMapper.toEntity(commercialRoomDTO);
        CommercialRoom savedCommercialRoom = commercialRoomRepository.save(commercialRoom);
        return commercialRoomMapper.toDTO(savedCommercialRoom);
    }

    @Transactional(readOnly = true)
    public CommercialRoomDTO getCommercialRoomByCode(String propertyCode) {
        CommercialRoom commercialRoom = commercialRoomRepository.findByPropertyCode(propertyCode);
        if (commercialRoom == null) {
            throw new EntityNotFoundException("Commercial Room not found with PropertyCode: " + propertyCode);
        }
        return commercialRoomMapper.toDTO(commercialRoom);
    }

    @Transactional(readOnly = true)
    public Page<CommercialRoomDTO> getAllCommercialRooms(Pageable pageable) {
        return commercialRoomRepository.findAll(pageable)
                .map(commercialRoomMapper::toDTO);
    }

    @Transactional
    public CommercialRoomDTO updateCommercialRoom(String propertyCode, CommercialRoomDTO commercialRoomDTO) {
        CommercialRoom existingEntity = commercialRoomRepository.findByPropertyCode(propertyCode);
        if (existingEntity == null) {
            throw new EntityNotFoundException("Commercial Room not found with PropertyCode: " + propertyCode);
        }
        Seller seller = sellerRepository.findById(commercialRoomDTO.getSellerId())
                .orElseThrow(() -> new EntityNotFoundException("Seller not found with ID: " + commercialRoomDTO.getSellerId()));
        Agent agent = agentRepository.findById(commercialRoomDTO.getAgentId())
                .orElseThrow(() -> new EntityNotFoundException("Agent not found with ID: " + commercialRoomDTO.getAgentId()));
        commercialRoomMapper.updateEntityFromDTO(commercialRoomDTO, existingEntity);
        existingEntity.setSeller(seller);
        existingEntity.setAgent(agent);
        CommercialRoom updatedEntity = commercialRoomRepository.save(existingEntity);
        return commercialRoomMapper.toDTO(updatedEntity);
    }

    @Transactional
    public void deleteCommercialRoom(String propertyCode) {
        CommercialRoom commercialRoom = commercialRoomRepository.findByPropertyCode(propertyCode);
        if (commercialRoom == null) {
            throw new EntityNotFoundException("Commercial Room not found with PropertyCode: " + propertyCode);
        }
        commercialRoomRepository.delete(commercialRoom);
    }
}
