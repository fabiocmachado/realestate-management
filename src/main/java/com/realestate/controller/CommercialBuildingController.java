package com.realestate.controller;

import com.realestate.dto.CommercialBuildingDTO;
import com.realestate.service.CommercialBuildingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/properties/commercialbuildings")
@RequiredArgsConstructor
public class CommercialBuildingController {

    private final CommercialBuildingService commercialBuildingService;

    @PostMapping
    public ResponseEntity<CommercialBuildingDTO> create(@RequestBody CommercialBuildingDTO commercialBuildingDTO) {
        CommercialBuildingDTO created = commercialBuildingService.createCommercialBuilding(commercialBuildingDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{propertyCode}")
    public ResponseEntity<CommercialBuildingDTO> getByPropertyCode(@PathVariable String propertyCode) {
        CommercialBuildingDTO dto = commercialBuildingService.getCommercialBuildingByCode(propertyCode);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<Page<CommercialBuildingDTO>> getAll(Pageable pageable) {
        Page<CommercialBuildingDTO> page = commercialBuildingService.getAllCommercialBuildings(pageable);
        return ResponseEntity.ok(page);
    }

    @PutMapping("/{propertyCode}")
    public ResponseEntity<CommercialBuildingDTO> update(@PathVariable String propertyCode, @RequestBody CommercialBuildingDTO commercialBuildingDTO) {
        CommercialBuildingDTO updated = commercialBuildingService.updateCommercialBuilding(propertyCode, commercialBuildingDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{propertyCode}")
    public ResponseEntity<Void> delete(@PathVariable String propertyCode) {
        commercialBuildingService.deleteCommercialBuilding(propertyCode);
        return ResponseEntity.noContent().build();
    }
}
