package com.realestate.controller;

import com.realestate.dto.PropertyDTO;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.service.PdfGenerationService;
import com.realestate.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/properties")
@RequiredArgsConstructor
public class PropertyController {

    private final PdfGenerationService pdfGenerationService;
    private final PropertyService propertyService;

    @GetMapping("/{propertyCode}/generate-pdf")
    public ResponseEntity<byte[]> generatePropertyPdf(@PathVariable String propertyCode) {
        try {
            PropertyDTO propertyDTO = propertyService.getPropertyDTOByCode(propertyCode);
            byte[] pdfContent = pdfGenerationService.generatePropertyPdf(propertyDTO);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/pdf");
            headers.add("Content-Disposition", "inline; filename=ficha_cadastral_propriedade.pdf");
            return new ResponseEntity<>(pdfContent, headers, HttpStatus.OK);

        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<Page<PropertyDTO>> getAllProperties(Pageable pageable) {
        Page<PropertyDTO> propertyDTOPage = propertyService.getAllProperties(pageable);
        return new ResponseEntity<>(propertyDTOPage, HttpStatus.OK);
    }
}
