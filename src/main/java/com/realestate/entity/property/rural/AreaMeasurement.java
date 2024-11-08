package com.realestate.entity.property.rural;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AreaMeasurement {

    @Column(name = "area_hectares")
    private Double hectares;

    @Column(name = "area_alqueires")
    private Double alqueiresGoianos;

    private static final float ALQUEIRE_TO_HECTARE = 4.84f;

    public AreaMeasurement(Float hectares) {
        setHectares(hectares);
    }

    public void setHectares(Float hectares) {
        this.hectares = Double.valueOf(hectares);
        this.alqueiresGoianos = Double.valueOf(hectares != null ? hectares / ALQUEIRE_TO_HECTARE : null);
    }

    public void setAlqueiresGoianos(Float alqueires) {
        this.alqueiresGoianos = Double.valueOf(alqueires);
        this.hectares = Double.valueOf(alqueires != null ? alqueires * ALQUEIRE_TO_HECTARE : null);
    }

    @Override
    public String toString() {
        if (hectares == null) return "Área não informada";
        return String.format("%.2f ha (%.2f alq. GO)", hectares, alqueiresGoianos);
    }
}
