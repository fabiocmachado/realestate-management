package com.realestate.controller;

import com.realestate.dto.*;
import com.realestate.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/properties")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;

    @GetMapping()
    public Page<PropertyDTO> getAllProperties(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return propertyService.getAllProperties(pageable);
    }

    @GetMapping("/{propertyCode}")
    public ResponseEntity<PropertyDTO> getPropertyByCode(@PathVariable String propertyCode) {
        return propertyService.getPropertyByCode(propertyCode)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
