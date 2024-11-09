package com.realestate.controller;

import com.realestate.dto.TownhouseDTO;
import com.realestate.service.TownhouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/townhouses")
@RequiredArgsConstructor
public class TownhouseController {
    private final TownhouseService townhouseService;

    @PostMapping
    public ResponseEntity<TownhouseDTO> createTownhouse(@RequestBody TownhouseDTO townhouseDTO) {
        return new ResponseEntity<>(townhouseService.createTownhouse(townhouseDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{propertyCode}")
    public ResponseEntity<TownhouseDTO> getTownhouseByCode(@PathVariable String propertyCode) {
        return ResponseEntity.ok(townhouseService.getTownhouseByCode(propertyCode));
    }

    @GetMapping
    public Page<TownhouseDTO> getAllTownhouses(Pageable pageable) {
        return townhouseService.getAllTownhouses(pageable);
    }

    @PutMapping("/{propertyCode}")
    public ResponseEntity<TownhouseDTO> updateTownhouse(
            @PathVariable String propertyCode,
            @RequestBody TownhouseDTO townhouseDTO
    ) {
        return ResponseEntity.ok(townhouseService.updateTownhouse(propertyCode, townhouseDTO));
    }

    @DeleteMapping("/{propertyCode}")
    public ResponseEntity<Void> deleteTownhouse(@PathVariable String propertyCode) {
        townhouseService.deleteTownhouse(propertyCode);
        return ResponseEntity.noContent().build();
    }
}
