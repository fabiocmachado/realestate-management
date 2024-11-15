package com.realestate.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;
import com.realestate.dto.PropertyDTO;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;

@Service
public class PdfGenerationService {

    public byte[] generatePropertyPdf(PropertyDTO propertyDTO) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        document.add(new Paragraph("Ficha Cadastral")
                .setFontSize(20)
                .setBold()
                .setTextAlignment(com.itextpdf.layout.properties.TextAlignment.CENTER)
                .setMarginBottom(20));

        Table table = new Table(UnitValue.createPercentArray(new float[]{1, 3}))
                .useAllAvailableWidth()
                .setMarginBottom(20);

        addPropertyFields(table, propertyDTO);

        document.add(table);
        document.close();

        return byteArrayOutputStream.toByteArray();
    }

    private void addPropertyFields(Table table, PropertyDTO propertyDTO) {
        Class<?> currentClass = propertyDTO.getClass();
        while (currentClass != null) {
            Field[] fields = currentClass.getDeclaredFields();
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    Object value = field.get(propertyDTO);
                    String fieldName = field.getName();
                    String fieldValue = value != null ? value.toString() : "";

                    table.addCell(fieldName);
                    table.addCell(fieldValue);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            currentClass = currentClass.getSuperclass();
        }
    }

}
