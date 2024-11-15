package com.realestate.controller;

import com.realestate.dto.*;
import com.realestate.entity.property.urban.residential.*;
import com.realestate.service.PdfGenerationService;
import com.realestate.entity.property.Property;
import com.realestate.repository.PropertyRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Optional;

@RestController
@RequestMapping("/properties")
public class PdfController {

    private final PdfGenerationService pdfGenerationService;
    private final PropertyRepository propertyRepository;

    public PdfController(PdfGenerationService pdfGenerationService, PropertyRepository propertyRepository) {
        this.pdfGenerationService = pdfGenerationService;
        this.propertyRepository = propertyRepository;
    }

    @GetMapping("/{propertyType}/empty-pdf")
    public ResponseEntity<byte[]> generateEmptyPropertyPdf(@PathVariable String propertyType) {
        try {
            // Cria um DTO vazio com base no tipo de propriedade
            PropertyDTO propertyDTO = createEmptyPropertyDto(propertyType);

            // Gera o PDF
            byte[] pdfBytes = pdfGenerationService.generatePropertyPdf(propertyDTO);

            // Configura o cabeçalho da resposta
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=ficha_cadastral_" + propertyType + ".pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);
        } catch (IllegalArgumentException e) {
            // Tipo de propriedade inválido
            return ResponseEntity.badRequest().body(null);
        } catch (IOException e) {
            // Erro interno no servidor
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @GetMapping("/{propertyCode}/pdf")
    public ResponseEntity<byte[]> generatePropertyPdf(@PathVariable String propertyCode) {
        Optional<Property> propertyOptional = propertyRepository.getPropertyByPropertyCode(propertyCode);

        if (!propertyOptional.isPresent()) {
            return ResponseEntity.status(404).body(("Property not found with code: " + propertyCode).getBytes());
        }

        Property property = propertyOptional.get();
        PropertyDTO propertyDTO = mapPropertyToDto(property);

        try {
            byte[] pdfBytes = pdfGenerationService.generatePropertyPdf(propertyDTO);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=ficha_cadastral_" + propertyCode + ".pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    private PropertyDTO mapPropertyToDto(Property property) {
        PropertyDTO propertyDTO;

        // Verifica o tipo da propriedade e cria o DTO apropriado
        if (property instanceof Apartment) {
            propertyDTO = new ApartmentDTO();
        } else if (property instanceof House) {
            propertyDTO = new HouseDTO();
        } else if (property instanceof Townhouse) {
            propertyDTO = new TownhouseDTO();
        } else if (property instanceof UrbanLand) {
            propertyDTO = new UrbanLandDTO();
        } else if (property instanceof Penthouse) {
            propertyDTO = new PenthouseDTO();
        } else {
            throw new IllegalArgumentException("Tipo de propriedade desconhecido: " + property.getClass().getSimpleName());
        }

        // Preenche os campos do DTO usando reflexão
        mapFields(property, propertyDTO);

        return propertyDTO;
    }

    private void mapFields(Object source, Object target) {
        Class<?> currentClass = source.getClass();
        while (currentClass != null) {
            Field[] fields = currentClass.getDeclaredFields();
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    Object value = field.get(source);

                    Field targetField = getField(target.getClass(), field.getName());
                    if (targetField != null) {
                        targetField.setAccessible(true);
                        targetField.set(target, value);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            currentClass = currentClass.getSuperclass();
        }
    }

    private Field getField(Class<?> clazz, String fieldName) {
        Class<?> currentClass = clazz;
        while (currentClass != null) {
            try {
                return currentClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                currentClass = currentClass.getSuperclass();
            }
        }
        return null;
    }


    private PropertyDTO createEmptyPropertyDto(String propertyType) {
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
                throw new IllegalArgumentException("Unknown property type: " + propertyType);
        }
    }
}

