package com.realestate.controller;

import com.realestate.dto.SellerDTO;
import com.realestate.service.SellerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sellers")
public class SellerController {

    private final SellerService sellerService;

    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @GetMapping
    public ResponseEntity<List<SellerDTO>> findAll() {
        return ResponseEntity.ok(sellerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SellerDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(sellerService.findById(id));
    }

    @PostMapping
    public ResponseEntity<SellerDTO> create(@Valid @RequestBody SellerDTO sellerDTO) {
        return ResponseEntity.status(201).body(sellerService.create(sellerDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SellerDTO> update(@PathVariable Long id, @Valid @RequestBody SellerDTO sellerDTO) {
        return ResponseEntity.ok(sellerService.update(id, sellerDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        sellerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
