package com.realestate.controller;

import com.realestate.dto.HouseDTO;
import com.realestate.service.HouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/houses")
@RequiredArgsConstructor
public class HouseController {

    private final HouseService houseService;

    @PostMapping
    public ResponseEntity<HouseDTO> createHouse(@RequestBody HouseDTO houseDTO) {
        return new ResponseEntity<>(houseService.createHouse(houseDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{propertyCode}")
    public ResponseEntity<HouseDTO> getHouseByCode(@PathVariable String propertyCode) {
        return ResponseEntity.ok(houseService.getHouseByCode(propertyCode));
    }

    @GetMapping
    public Page<HouseDTO> getAllHouses(Pageable pageable) {
        return houseService.getAllHouses(pageable);
    }

    @PutMapping("/{propertyCode}")
    public ResponseEntity<HouseDTO> updateHouse(
            @PathVariable String propertyCode,
            @RequestBody HouseDTO houseDTO
    ) {
        return ResponseEntity.ok(houseService.updateHouse(propertyCode, houseDTO));
    }

    @DeleteMapping("/{propertyCode}")
    public ResponseEntity<Void> deleteHouse(@PathVariable String propertyCode) {
        houseService.deleteHouse(propertyCode);
        return ResponseEntity.noContent().build();
    }
}
