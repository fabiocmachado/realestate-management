package com.realestate.controller;

import com.realestate.dto.ApartmentDTO;
import com.realestate.service.ApartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/apartments")
@RequiredArgsConstructor
public class ApartmentController {

    private final ApartmentService apartmentService;

    @PostMapping
    public ResponseEntity<ApartmentDTO> createApartment(
            @Valid @RequestBody ApartmentDTO apartmentDTO
    ) {
        ApartmentDTO createdApartment = apartmentService.createApartment(apartmentDTO);
        return new ResponseEntity<>(createdApartment, HttpStatus.CREATED);
    }

    @GetMapping("/{propertyCode}")
    public ResponseEntity<ApartmentDTO> getApartmentByCode(
            @PathVariable String propertyCode
    ) {
        ApartmentDTO apartment = apartmentService.getApartmentByCode(propertyCode);
        return ResponseEntity.ok(apartment);
    }

    @GetMapping
    public ResponseEntity<Page<ApartmentDTO>> getAllApartments(Pageable pageable) {
        Page<ApartmentDTO> apartments = apartmentService.getAllApartments(pageable);
        return ResponseEntity.ok(apartments);
    }

    @PutMapping("/{propertyCode}")
    public ResponseEntity<ApartmentDTO> updateApartment(
            @PathVariable String propertyCode,
            @Valid @RequestBody ApartmentDTO apartmentDTO
    ) {
        ApartmentDTO updatedApartment = apartmentService.updateApartment(propertyCode, apartmentDTO);
        return ResponseEntity.ok(updatedApartment);
    }

    @DeleteMapping("/{propertyCode}")
    public ResponseEntity<Void> deleteApartment(
            @PathVariable String propertyCode
    ) {
        apartmentService.deleteApartment(propertyCode);
        return ResponseEntity.noContent().build();
    }
}