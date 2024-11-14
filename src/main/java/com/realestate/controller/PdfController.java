package com.realestate.controller;

import com.realestate.dto.PropertyDTO;
import com.realestate.service.PdfGenerationService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/pdf")
public class PdfController {

    private final PdfGenerationService pdfGenerationService;

    public PdfController(PdfGenerationService pdfGenerationService) {
        this.pdfGenerationService = pdfGenerationService;
    }

    @GetMapping("/property")
    public ResponseEntity<byte[]> generatePropertyPdf(@RequestParam String propertyCode) throws IOException {
        // Aqui você pode obter o PropertyDTO usando o propertyCode, por exemplo, de um banco de dados
        PropertyDTO propertyDTO = getPropertyByCode(propertyCode);

        // Gerando o PDF
        byte[] pdfBytes = pdfGenerationService.generatePropertyPdf(propertyDTO);

        // Definindo cabeçalhos para resposta HTTP
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=property.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }

    // Método de exemplo para buscar um PropertyDTO (aqui você pode implementar a lógica de busca real)
    private PropertyDTO getPropertyByCode(String propertyCode) {
        // Exemplo simples, substitua com a lógica real de busca no banco de dados
        return PropertyDTO.builder()
                .propertyCode(propertyCode)
                .price(new java.math.BigDecimal("250000.00"))
                .address("Rua Exemplo, 123")
                .description("Uma propriedade muito boa.")
                .build();
    }
}
