package com.realestate.controller;

import com.realestate.dto.*;
import com.realestate.entity.property.rural.CountryHouse;
import com.realestate.entity.property.rural.Farm;
import com.realestate.entity.property.urban.comercial.CommercialArea;
import com.realestate.entity.property.urban.comercial.CommercialBuilding;
import com.realestate.entity.property.urban.comercial.Warehouse;
import com.realestate.entity.property.urban.residential.*;
import com.realestate.entity.property.*;
import com.realestate.service.PdfGenerationService;
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
            PropertyDTO propertyDTO = createEmptyPropertyDto(propertyType);

            byte[] pdfBytes = pdfGenerationService.generatePropertyPdf(propertyDTO);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=ficha_cadastral_" + propertyType + ".pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @GetMapping("/{propertyType}/{propertyCode}/pdf")
    public ResponseEntity<byte[]> generatePropertyPdf(@PathVariable String propertyType, @PathVariable String propertyCode) {
        Optional<Property> propertyOptional = propertyRepository.getPropertyByPropertyCode(propertyCode);

        if (propertyOptional.isEmpty()) {
            return ResponseEntity.status(404).body(("Propriedade não encontrada com código: " + propertyCode).getBytes());
        }

        Property property = propertyOptional.get();
        PropertyDTO propertyDTO = mapPropertyToDto(property);

        try {
            byte[] pdfBytes = pdfGenerationService.generatePropertyPdf(propertyDTO);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=ficha_cadastral_" + propertyType + "_" + propertyCode + ".pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(("Erro ao gerar o PDF: " + e.getMessage()).getBytes());
        }
    }

    private PropertyDTO mapPropertyToDto(Property property) {
        PropertyDTO propertyDTO;

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
        } else if (property instanceof CommercialArea) {
            propertyDTO = new CommercialAreaDTO();
        } else if (property instanceof CommercialBuilding) {
            propertyDTO = new CommercialBuildingDTO();
        } else if (property instanceof CountryHouse) {
            propertyDTO = new CountryHouseDTO();
        } else if (property instanceof Farm) {
            propertyDTO = new FarmDTO();
        } else if (property instanceof Warehouse) {
            propertyDTO = new WarehouseDTO();
        } else {
            throw new IllegalArgumentException("Tipo de propriedade desconhecido: " + property.getClass().getSimpleName());
        }

        mapFields(property, propertyDTO);

        return propertyDTO;
    }

    private PropertyDTO createEmptyPropertyDto(String propertyType) {
        PropertyDTO propertyDTO;
        switch (propertyType.toLowerCase()) {
            case "apartment":
                propertyDTO = new ApartmentDTO();
                propertyDTO.setPropertyCategory("Apartment");
                break;
            case "house":
                propertyDTO = new HouseDTO();
                propertyDTO.setPropertyCategory("House");
                break;
            case "townhouse":
                propertyDTO = new TownhouseDTO();
                propertyDTO.setPropertyCategory("Townhouse");
                break;
            case "urban-land":
                propertyDTO = new UrbanLandDTO();
                propertyDTO.setPropertyCategory("Urban Land");
                break;
            case "penthouse":
                propertyDTO = new PenthouseDTO();
                propertyDTO.setPropertyCategory("Penthouse");
                break;
            case "commercial-area":
                propertyDTO = new CommercialAreaDTO();
                propertyDTO.setPropertyCategory("Commercial Area");
                break;
            case "commercial-building":
                propertyDTO = new CommercialBuildingDTO();
                propertyDTO.setPropertyCategory("Commercial Building");
                break;
            case "country-house":
                propertyDTO = new CountryHouseDTO();
                propertyDTO.setPropertyCategory("Country House");
                break;
            case "farm":
                propertyDTO = new FarmDTO();
                propertyDTO.setPropertyCategory("Farm");
                break;
            case "warehouse":
                propertyDTO = new WarehouseDTO();
                propertyDTO.setPropertyCategory("Warehouse");
                break;
            default:
                throw new IllegalArgumentException("Tipo de imóvel desconhecido: " + propertyType);
        }
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
}
