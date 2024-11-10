package com.realestate.controller;

import com.realestate.dto.CommercialAreaDTO;
import com.realestate.service.CommercialAreaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/commercial-areas")
@RequiredArgsConstructor
public class CommercialAreaController {

    private final CommercialAreaService commercialAreaService;

    @PostMapping
    public ResponseEntity<CommercialAreaDTO> createCommercialArea(@Valid @RequestBody CommercialAreaDTO commercialAreaDTO) {
        CommercialAreaDTO createdCommercialArea = commercialAreaService.createCommercialArea(commercialAreaDTO);
        return new ResponseEntity<>(createdCommercialArea, HttpStatus.CREATED);
    }

    @GetMapping("/{propertyCode}")
    public ResponseEntity<CommercialAreaDTO> getCommercialArea(@PathVariable String propertyCode) {
        CommercialAreaDTO commercialArea = commercialAreaService.getCommercialAreaByCode(propertyCode);
        return ResponseEntity.ok(commercialArea);
    }

    @GetMapping
    public ResponseEntity<Page<CommercialAreaDTO>> getAllCommercialAreas(Pageable pageable) {
        Page<CommercialAreaDTO> commercialAreas = commercialAreaService.getAllCommercialAreas(pageable);
        return ResponseEntity.ok(commercialAreas);
    }

    @PutMapping("/{propertyCode}")
    public ResponseEntity<CommercialAreaDTO> updateCommercialArea(
            @PathVariable String propertyCode,
            @Valid @RequestBody CommercialAreaDTO commercialAreaDTO) {
        CommercialAreaDTO updatedCommercialArea = commercialAreaService.updateCommercialArea(propertyCode, commercialAreaDTO);
        return ResponseEntity.ok(updatedCommercialArea);
    }

    @DeleteMapping("/{propertyCode}")
    public ResponseEntity<Void> deleteCommercialArea(@PathVariable String propertyCode) {
        commercialAreaService.deleteCommercialArea(propertyCode);
        return ResponseEntity.noContent().build();
    }
}