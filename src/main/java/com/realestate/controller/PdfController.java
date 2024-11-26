package com.realestate.controller;

import com.realestate.dto.PropertyDTO;
import com.realestate.entity.property.Property;
import com.realestate.entity.property.rural.CountryHouse;
import com.realestate.entity.property.rural.Farm;
import com.realestate.entity.property.urban.comercial.CommercialArea;
import com.realestate.entity.property.urban.comercial.CommercialBuilding;
import com.realestate.entity.property.urban.comercial.Warehouse;
import com.realestate.entity.property.urban.residential.*;
import com.realestate.mapper.*;
import com.realestate.repository.PropertyRepository;
import com.realestate.service.PdfGenerationService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/properties")
public class PdfController {

    private final PdfGenerationService pdfGenerationService;
    private final PropertyRepository propertyRepository;
    private final ApartmentMapper apartmentMapper;
    private final FarmMapper farmMapper;
    private final HouseMapper houseMapper;
    private final TownhouseMapper townhouseMapper;
    private final UrbanLandMapper urbanLandMapper;
    private final PenthouseMapper penthouseMapper;
    private final CommercialAreaMapper commercialAreaMapper;
    private final CommercialBuildingMapper commercialBuildingMapper;
    private final CountryHouseMapper countryHouseMapper;
    private final WarehouseMapper warehouseMapper;

    public PdfController(PdfGenerationService pdfGenerationService,
                         PropertyRepository propertyRepository,
                         ApartmentMapper apartmentMapper,
                         FarmMapper farmMapper,
                         HouseMapper houseMapper,
                         TownhouseMapper townhouseMapper,
                         UrbanLandMapper urbanLandMapper,
                         PenthouseMapper penthouseMapper,
                         CommercialAreaMapper commercialAreaMapper,
                         CommercialBuildingMapper commercialBuildingMapper,
                         CountryHouseMapper countryHouseMapper,
                         WarehouseMapper warehouseMapper) {
        this.pdfGenerationService = pdfGenerationService;
        this.propertyRepository = propertyRepository;
        this.apartmentMapper = apartmentMapper;
        this.farmMapper = farmMapper;
        this.houseMapper = houseMapper;
        this.townhouseMapper = townhouseMapper;
        this.urbanLandMapper = urbanLandMapper;
        this.penthouseMapper = penthouseMapper;
        this.commercialAreaMapper = commercialAreaMapper;
        this.commercialBuildingMapper = commercialBuildingMapper;
        this.countryHouseMapper = countryHouseMapper;
        this.warehouseMapper = warehouseMapper;
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
        if (property instanceof Apartment) {
            return apartmentMapper.toDto((Apartment) property);
        } else if (property instanceof Farm) {
            return farmMapper.toDTO((Farm) property);
        } else if (property instanceof House) {
            return houseMapper.toDto((House) property);
        } else if (property instanceof Townhouse) {
            return townhouseMapper.toDto((Townhouse) property);
        } else if (property instanceof UrbanLand) {
            return urbanLandMapper.toDTO((UrbanLand) property);
        } else if (property instanceof Penthouse) {
            return penthouseMapper.toDto((Penthouse) property);
        } else if (property instanceof CommercialArea) {
            return commercialAreaMapper.toDTO((CommercialArea) property);
        } else if (property instanceof CommercialBuilding) {
            return commercialBuildingMapper.toDTO((CommercialBuilding) property);
        } else if (property instanceof CountryHouse) {
            return countryHouseMapper.toDTO((CountryHouse) property);
        } else if (property instanceof Warehouse) {
            return warehouseMapper.toDTO((Warehouse) property);
        }
        throw new IllegalArgumentException("Tipo de propriedade desconhecido: " + property.getClass().getSimpleName());
    }

    private PropertyDTO createEmptyPropertyDto(String propertyType) {
        switch (propertyType.toLowerCase()) {
            case "apartment":
                return apartmentMapper.toDto(new Apartment());
            case "farm":
                return farmMapper.toDTO(new Farm());
            case "house":
                return houseMapper.toDto(new House());
            case "townhouse":
                return townhouseMapper.toDto(new Townhouse());
            case "urban-land":
                return urbanLandMapper.toDTO(new UrbanLand());
            case "penthouse":
                return penthouseMapper.toDto(new Penthouse());
            case "commercial-area":
                return commercialAreaMapper.toDTO(new CommercialArea());
            case "commercial-building":
                return commercialBuildingMapper.toDTO(new CommercialBuilding());
            case "country-house":
                return countryHouseMapper.toDTO(new CountryHouse());
            case "warehouse":
                return warehouseMapper.toDTO(new Warehouse());
            default:
                throw new IllegalArgumentException("Tipo de imóvel desconhecido: " + propertyType);
        }
    }
}
