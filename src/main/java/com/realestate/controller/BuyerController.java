package com.realestate.controller;

import com.realestate.dto.BuyerDTO;
import com.realestate.service.BuyerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/buyers")
public class BuyerController {

    private final BuyerService buyerService;

    @Autowired
    public BuyerController(BuyerService buyerService) {
        this.buyerService = buyerService;
    }

    @GetMapping
    public ResponseEntity<List<BuyerDTO>> findAllBuyers() {
        return ResponseEntity.ok(buyerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BuyerDTO> findBuyerById(@PathVariable Long id) {
        return ResponseEntity.ok(buyerService.findById(id));
    }

    @PostMapping
    public ResponseEntity<BuyerDTO> createBuyer(@Valid @RequestBody BuyerDTO buyerDTO) {
        BuyerDTO createdBuyer = buyerService.create(buyerDTO);
        return new ResponseEntity<>(createdBuyer, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BuyerDTO> updateBuyer(@PathVariable Long id, @Valid @RequestBody BuyerDTO buyerDTO) {
        return ResponseEntity.ok(buyerService.update(id, buyerDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBuyer(@PathVariable Long id) {
        buyerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}