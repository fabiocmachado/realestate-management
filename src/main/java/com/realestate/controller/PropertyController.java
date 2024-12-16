package com.realestate.controller;

import com.realestate.dto.PropertyDTO;
import com.realestate.service.PropertyService;
import com.realestate.exception.InvalidCategoryException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/properties")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 10;

    @GetMapping
    public ResponseEntity<Page<PropertyDTO>> getProperties(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "" + DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = "" + DEFAULT_SIZE) int size) {

        Pageable pageable = createPageable(page, size);

        try {
            Page<PropertyDTO> propertyDTOs = propertyService.getProperties(status, category, pageable);

            return propertyDTOs.isEmpty()
                    ? ResponseEntity.noContent().build()
                    : ResponseEntity.ok(propertyDTOs);
        } catch (InvalidCategoryException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    private Pageable createPageable(int page, int size) {
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("Os parâmetros 'page' e 'size' devem ser válidos. 'page' não pode ser negativo e 'size' deve ser maior que zero.");
        }
        return PageRequest.of(page, size);
    }
}
