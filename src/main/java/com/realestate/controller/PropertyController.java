package com.realestate.controller;

import com.realestate.dto.PropertyDTO;
import com.realestate.dto.PropertyPageDTO;
import com.realestate.entity.property.Property;
import com.realestate.service.PropertyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/properties")
public class PropertyController {

    private final PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @GetMapping
    public ResponseEntity<PropertyPageDTO> listProperties(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Property> propertyPage = propertyService.getProperties(status, category, pageable);

        List<PropertyDTO> dtos = propertyPage.stream()
                .map(this::convertToDTO)
                .toList();

        PropertyPageDTO response = new PropertyPageDTO(
                dtos,
                propertyPage.getNumber(),
                propertyPage.getSize(),
                propertyPage.getTotalElements(),
                propertyPage.getTotalPages()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{propertyCode}")
    public ResponseEntity<PropertyDTO> getPropertyByCode(@PathVariable String propertyCode) {
        Property property = propertyService.getPropertyByCode(propertyCode);
        return ResponseEntity.ok(convertToDTO(property));
    }

    private PropertyDTO convertToDTO(Property property) {
        PropertyDTO dto = new PropertyDTO();
        dto.setId(property.getId());
        dto.setPropertyCode(property.getPropertyCode());
        dto.setPrice(property.getPrice());
        dto.setDescription(property.getDescription());
        dto.setStatus(property.getStatus());
        dto.setSellerId(property.getSeller().getId());
        dto.setAgentId(property.getAgent().getId());
        dto.setPropertyCategory(property.getPropertyCategory());
        dto.setStreet(property.getStreet());
        dto.setBlock(property.getBlock());
        dto.setLot(property.getLot());
        dto.setNumber(property.getNumber());
        dto.setComplement(property.getComplement());
        dto.setCity(property.getCity());
        dto.setState(property.getState());
        dto.setCreatedAt(property.getCreatedAt());
        dto.setUpdatedAt(property.getUpdatedAt());
        dto.setUsableArea(property.getUsableArea());
        dto.setPrivateArea(property.getPrivateArea());
        dto.setTotalArea(property.getTotalArea());
        dto.setVisitingTime(property.getVisitingTime());
        return dto;
    }
}
