package com.realestate.controller;

import com.realestate.dto.PenthouseDTO;
import com.realestate.service.PenthouseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/penthouses")
@RequiredArgsConstructor
public class PenthouseController {

    private final PenthouseService penthouseService;

    @PostMapping
    public ResponseEntity<PenthouseDTO> createPenthouse(
            @Valid @RequestBody PenthouseDTO penthouseDTO
    ) {
        PenthouseDTO createdPenthouse = penthouseService.createPenthouse(penthouseDTO);
        return new ResponseEntity<>(createdPenthouse, HttpStatus.CREATED);
    }

    @GetMapping("/{propertyCode}")
    public ResponseEntity<PenthouseDTO> getPenthouseByCode(
            @PathVariable String propertyCode
    ) {
        PenthouseDTO penthouse = penthouseService.getPenthouseByCode(propertyCode);
        return ResponseEntity.ok(penthouse);
    }

    @GetMapping
    public ResponseEntity<Page<PenthouseDTO>> getAllPenthouses(Pageable pageable) {
        Page<PenthouseDTO> penthouses = penthouseService.getAllPenthouses(pageable);
        return ResponseEntity.ok(penthouses);
    }

    @PutMapping("/{propertyCode}")
    public ResponseEntity<PenthouseDTO> updatePenthouse(
            @PathVariable String propertyCode,
            @Valid @RequestBody PenthouseDTO penthouseDTO
    ) {
        PenthouseDTO updatedPenthouse = penthouseService.updatePenthouse(propertyCode, penthouseDTO);
        return ResponseEntity.ok(updatedPenthouse);
    }

    @DeleteMapping("/{propertyCode}")
    public ResponseEntity<Void> deletePenthouse(
            @PathVariable String propertyCode
    ) {
        penthouseService.deletePenthouse(propertyCode);
        return ResponseEntity.noContent().build();
    }
}