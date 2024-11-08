package com.realestate.controller;

import com.realestate.dto.UrbanLandDTO;
import com.realestate.service.UrbanLandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/urban-lands")
@RequiredArgsConstructor
public class UrbanLandController {

    private final UrbanLandService urbanLandService;

    @PostMapping
    public ResponseEntity<UrbanLandDTO> createUrbanLand(
            @Valid @RequestBody UrbanLandDTO urbanLandDTO
    ) {
        UrbanLandDTO createdUrbanLand = urbanLandService.createUrbanLand(urbanLandDTO);
        return new ResponseEntity<>(createdUrbanLand, HttpStatus.CREATED);
    }

    @GetMapping("/{propertyCode}")
    public ResponseEntity<UrbanLandDTO> getUrbanLandByCode(
            @PathVariable String propertyCode
    ) {
        UrbanLandDTO urbanLand = urbanLandService.getUrbanLandByCode(propertyCode);
        return ResponseEntity.ok(urbanLand);
    }

    @GetMapping
    public ResponseEntity<Page<UrbanLandDTO>> getAllUrbanLands(Pageable pageable) {
        Page<UrbanLandDTO> urbanLands = urbanLandService.getAllUrbanLands(pageable);
        return ResponseEntity.ok(urbanLands);
    }

    @PutMapping("/{propertyCode}")
    public ResponseEntity<UrbanLandDTO> updateUrbanLand(
            @PathVariable String propertyCode,
            @Valid @RequestBody UrbanLandDTO urbanLandDTO
    ) {
        UrbanLandDTO updatedUrbanLand = urbanLandService.updateUrbanLand(propertyCode, urbanLandDTO);
        return ResponseEntity.ok(updatedUrbanLand);
    }

    @DeleteMapping("/{propertyCode}")
    public ResponseEntity<Void> deleteUrbanLand(
            @PathVariable String propertyCode
    ) {
        urbanLandService.deleteUrbanLand(propertyCode);
        return ResponseEntity.noContent().build();
    }
}