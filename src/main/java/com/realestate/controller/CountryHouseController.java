package com.realestate.controller;

import com.realestate.dto.CountryHouseDTO;
import com.realestate.service.CountryHouseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/country-houses")
@RequiredArgsConstructor
public class CountryHouseController {

    private final CountryHouseService countryHouseService;

    @PostMapping
    public ResponseEntity<CountryHouseDTO> createCountryHouse(
            @Valid @RequestBody CountryHouseDTO countryHouseDTO
    ) {
        CountryHouseDTO createdCountryHouse = countryHouseService.createCountryHouse(countryHouseDTO);
        return new ResponseEntity<>(createdCountryHouse, HttpStatus.CREATED);
    }

    @GetMapping("/{propertyCode}")
    public ResponseEntity<CountryHouseDTO> getCountryHouseByCode(
            @PathVariable String propertyCode
    ) {
        CountryHouseDTO countryHouse = countryHouseService.getCountryHouseByCode(propertyCode);
        return ResponseEntity.ok(countryHouse);
    }

    @GetMapping
    public ResponseEntity<Page<CountryHouseDTO>> getAllCountryHouses(Pageable pageable) {
        Page<CountryHouseDTO> countryHouses = countryHouseService.getAllCountryHouses(pageable);
        return ResponseEntity.ok(countryHouses);
    }

    @PutMapping("/{propertyCode}")
    public ResponseEntity<CountryHouseDTO> updateCountryHouse(
            @PathVariable String propertyCode,
            @Valid @RequestBody CountryHouseDTO countryHouseDTO
    ) {
        CountryHouseDTO updatedCountryHouse = countryHouseService.updateCountryHouse(propertyCode, countryHouseDTO);
        return ResponseEntity.ok(updatedCountryHouse);
    }

    @DeleteMapping("/{propertyCode}")
    public ResponseEntity<Void> deleteCountryHouse(
            @PathVariable String propertyCode
    ) {
        countryHouseService.deleteCountryHouse(propertyCode);
        return ResponseEntity.noContent().build();
    }
}
