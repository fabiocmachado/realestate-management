package com.realestate.controller;

import com.realestate.dto.CommercialRoomDTO;
import com.realestate.entity.property.urban.comercial.CommercialRoom;
import com.realestate.repository.CommercialRoomRepository;
import com.realestate.service.CommercialRoomService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/properties/commercialrooms")
@RequiredArgsConstructor
public class CommercialRoomController {

    private final CommercialRoomService commercialRoomService;
    private final CommercialRoomRepository commercialRoomRepository;

    @PostMapping
    public ResponseEntity<CommercialRoomDTO> create(@RequestBody CommercialRoomDTO commercialRoomDTO) {
        CommercialRoomDTO created = commercialRoomService.createCommercialRoom(commercialRoomDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{propertyCode}")
    public ResponseEntity<CommercialRoomDTO> getByPropertyCode(@PathVariable String propertyCode) {
        CommercialRoomDTO dto = commercialRoomService.getCommercialRoomByCode(propertyCode);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<Page<CommercialRoomDTO>> getAll(Pageable pageable) {
        Page<CommercialRoomDTO> page = commercialRoomService.getAllCommercialRooms(pageable);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{propertyCode}")
    public ResponseEntity<CommercialRoomDTO> update(@PathVariable String propertyCode, @RequestBody CommercialRoomDTO commercialRoomDTO) {
        CommercialRoomDTO updated = commercialRoomService.updateCommercialRoom(propertyCode, commercialRoomDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{propertyCode}")
    public void delete(@PathVariable String propertyCode) {
        CommercialRoom commercialRoom = commercialRoomRepository.findByPropertyCode(propertyCode);
        if (commercialRoom == null) {
            throw new EntityNotFoundException("Apartment not found with code: " + propertyCode);
        }
        commercialRoomRepository.delete(commercialRoom);
    }
}
