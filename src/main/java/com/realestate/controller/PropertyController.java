package com.realestate.controller;

import com.realestate.dto.*;
import com.realestate.service.PdfGenerationService;
import com.realestate.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/properties")
@RequiredArgsConstructor
public class PropertyController {

    private final PdfGenerationService pdfGenerationService;
    private final PropertyService propertyService;

    @GetMapping()
    public Page<PropertyDTO> getAllProperties(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return propertyService.getAllProperties(pageable);
    }

    // Método para obter o DTO correto com base no tipo de imóvel
    private PropertyDTO getPropertyDTOByType(String propertyCode, String propertyType) {
        Class<? extends PropertyDTO> propertyClass = getPropertyClassByType(propertyType);
        return propertyService.getPropertyDTOByCode(propertyCode, propertyClass);
    }

    // Método para mapear o tipo de imóvel para a classe correspondente
    private Class<? extends PropertyDTO> getPropertyClassByType(String propertyType) {
        switch (propertyType.toLowerCase()) {
            case "apartment":
                return ApartmentDTO.class;
            case "house":
                return HouseDTO.class;
            case "townhouse":
                return TownhouseDTO.class;
            case "urbanland":
                return UrbanLandDTO.class;
            case "penthouse":
                return PenthouseDTO.class;
            default:
                throw new IllegalArgumentException("Tipo de imóvel desconhecido: " + propertyType);
        }
    }


    // Método para criar um DTO vazio com base no tipo de imóvel
    private PropertyDTO createEmptyPropertyDTO(String propertyType) {
        switch (propertyType.toLowerCase()) {
            case "apartment":
                return new ApartmentDTO();
            case "house":
                return new HouseDTO();
            case "townhouse":
                return new TownhouseDTO();
            case "urbanland":
                return new UrbanLandDTO();
            case "penthouse":
                return new PenthouseDTO();
            default:
                return null;
        }
    }

    // Método para gerar o PDF usando o PdfGenerationService
    private ResponseEntity<byte[]> generatePdf(PropertyDTO propertyDTO, String fileName) {
        try {
            byte[] pdfContent = pdfGenerationService.generatePropertyPdf(propertyDTO);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/pdf");
            headers.add("Content-Disposition", "inline; filename=" + fileName);
            return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
