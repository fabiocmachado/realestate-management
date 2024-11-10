package com.realestate.controller;

import com.realestate.dto.WarehouseDTO;
import com.realestate.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/warehouses")
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;

    @PostMapping
    public ResponseEntity<WarehouseDTO> createWarehouse(@RequestBody WarehouseDTO warehouseDTO) {
        WarehouseDTO createdWarehouse = warehouseService.createWarehouse(warehouseDTO);
        return ResponseEntity.ok(createdWarehouse);
    }

    @GetMapping("/{propertyCode}")
    public ResponseEntity<WarehouseDTO> getWarehouseByCode(@PathVariable String propertyCode) {
        WarehouseDTO warehouse = warehouseService.getWarehouseByCode(propertyCode);
        return ResponseEntity.ok(warehouse);
    }

    @GetMapping
    public ResponseEntity<Page<WarehouseDTO>> getAllWarehouses(Pageable pageable) {
        Page<WarehouseDTO> warehouses = warehouseService.getAllWarehouses(pageable);
        return ResponseEntity.ok(warehouses);
    }

    @PutMapping("/{propertyCode}")
    public ResponseEntity<WarehouseDTO> updateWarehouse(
            @PathVariable String propertyCode,
            @RequestBody WarehouseDTO warehouseDTO) {
        WarehouseDTO updatedWarehouse = warehouseService.updateWarehouse(propertyCode, warehouseDTO);
        return ResponseEntity.ok(updatedWarehouse);
    }

    @DeleteMapping("/{propertyCode}")
    public ResponseEntity<Void> deleteWarehouse(@PathVariable String propertyCode) {
        warehouseService.deleteWarehouse(propertyCode);
        return ResponseEntity.noContent().build();
    }
}