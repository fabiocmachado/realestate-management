package com.realestate.controller;

import com.realestate.dto.FarmDTO;
import com.realestate.service.FarmService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/farms")
@RequiredArgsConstructor
public class FarmController {

    private final FarmService farmService;

    @PostMapping
    public ResponseEntity<FarmDTO> createFarm(
            @Valid @RequestBody FarmDTO farmDTO
    ) {
        FarmDTO createdFarm = farmService.createFarm(farmDTO);
        return new ResponseEntity<>(createdFarm, HttpStatus.CREATED);
    }

    @GetMapping("/{propertyCode}")
    public ResponseEntity<FarmDTO> getFarmByCode(
            @PathVariable String propertyCode
    ) {
        FarmDTO farm = farmService.getFarmByCode(propertyCode);
        return ResponseEntity.ok(farm);
    }

    @GetMapping
    public ResponseEntity<Page<FarmDTO>> getAllFarms(Pageable pageable) {
        Page<FarmDTO> farms = farmService.getAllFarms(pageable);
        return ResponseEntity.ok(farms);
    }

    @PutMapping("/{propertyCode}")
    public ResponseEntity<FarmDTO> updateFarm(
            @PathVariable String propertyCode,
            @Valid @RequestBody FarmDTO farmDTO
    ) {
        FarmDTO updatedFarm = farmService.updateFarm(propertyCode, farmDTO);
        return ResponseEntity.ok(updatedFarm);
    }

    @DeleteMapping("/{propertyCode}")
    public ResponseEntity<Void> deleteFarm(
            @PathVariable String propertyCode
    ) {
        farmService.deleteFarm(propertyCode);
        return ResponseEntity.noContent().build();
    }
}
