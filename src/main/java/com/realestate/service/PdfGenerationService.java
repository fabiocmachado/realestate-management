package com.realestate.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.Document;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.realestate.dto.PropertyDTO;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class PdfGenerationService {

    public byte[] generatePropertyPdf(PropertyDTO propertyDTO) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // Criando o escritor e documento PDF
        PdfWriter writer = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        // Carregando a fonte padrão (Helvetica)
        PdfFont font = PdfFontFactory.createFont("Helvetica");

        // Adicionando título
        document.add(new Paragraph("Ficha Cadastral - Propriedade")
                .setFont(font)
                .setFontSize(20)
                .setBold()
                .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
                .setMarginBottom(20));

        // Criando tabela
        Table table = new Table(UnitValue.createPercentArray(new float[]{1, 3})) // Layout das colunas
                .useAllAvailableWidth()
                .setMarginBottom(20);

        // Adicionando campos genéricos da propriedade
        addPropertyField(table, "Código da Propriedade:", propertyDTO.getPropertyCode());
        addPropertyField(table, "Preço:", propertyDTO.getPrice() != null ? propertyDTO.getPrice().toString() : "______________________");
        addPropertyField(table, "Endereço:", propertyDTO.getAddress());
        addPropertyField(table, "Descrição:", propertyDTO.getDescription());

        // Adicionando a tabela ao documento e fechando
        document.add(table);
        document.close();

        return byteArrayOutputStream.toByteArray();
    }

    // Método auxiliar para adicionar um campo na tabela
    private void addPropertyField(Table table, String label, String value) {
        table.addCell(label);
        table.addCell(value != null ? value : "______________________");
    }
}
