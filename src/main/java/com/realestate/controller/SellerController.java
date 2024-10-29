package com.realestate.controller;

import com.realestate.dto.SellerDTO;
import com.realestate.service.SellerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sellers")
public class SellerController {

    private final SellerService sellerService;

    @Autowired
    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @GetMapping
    public ResponseEntity<List<SellerDTO>> findAllSellers() {
        return ResponseEntity.ok(sellerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SellerDTO> findSellerById(@PathVariable Long id) {
        return ResponseEntity.ok(sellerService.findById(id));
    }

    @PostMapping
    public ResponseEntity<SellerDTO> createSeller(@Valid @RequestBody SellerDTO sellerDTO) {
        SellerDTO createdSeller = sellerService.create(sellerDTO);
        return new ResponseEntity<>(createdSeller, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SellerDTO> updateSeller(@PathVariable Long id, @Valid @RequestBody SellerDTO sellerDTO) {
        return ResponseEntity.ok(sellerService.update(id, sellerDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeller(@PathVariable Long id) {
        sellerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}