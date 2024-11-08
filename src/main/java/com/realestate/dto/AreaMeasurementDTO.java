package com.realestate.dto;

import com.realestate.entity.property.rural.AreaMeasurement;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AreaMeasurementDTO {
    private Double alqueiresGoianos;
    private Double hectares;

    private static final double ALQUEIRE_TO_HECTARE = 4.84;

    private AreaMeasurementDTO(Double alqueiresGoianos, Double hectares) {
        this.alqueiresGoianos = alqueiresGoianos;
        this.hectares = hectares;
    }

    public AreaMeasurementDTO(Double alqueiresGoianos) {
        this.alqueiresGoianos = alqueiresGoianos;
        this.hectares = calculateHectares(alqueiresGoianos);
    }

    private Double calculateHectares(Double alqueiresGoianos) {
        return alqueiresGoianos != null ? alqueiresGoianos * ALQUEIRE_TO_HECTARE : null;
    }

    @Builder(builderMethodName = "customBuilder")
    public static AreaMeasurementDTO builder(Double alqueiresGoianos) {
        return new AreaMeasurementDTO(alqueiresGoianos, alqueiresGoianos * ALQUEIRE_TO_HECTARE);
    }

    public static AreaMeasurementDTO fromEntity(AreaMeasurement areaMeasurement) {
        return customBuilder()
                .alqueiresGoianos((double) areaMeasurement.getAlqueiresGoianos())  // Convertendo Float para Double
                .build();
    }

    public AreaMeasurement toEntity() {
        return AreaMeasurement.builder()
                .alqueiresGoianos(this.alqueiresGoianos)
                .hectares(this.hectares)
                .build();
    }

    public boolean isValid() {
        return alqueiresGoianos != null && alqueiresGoianos >= 0;
    }

    public String getFormattedArea() {
        if (!isValid()) {
            return "Área não informada";
        }
        return String.format("%.2f alq. GO (%.2f ha)", alqueiresGoianos, hectares);
    }
}
