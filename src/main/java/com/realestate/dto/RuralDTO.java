package com.realestate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.realestate.entity.property.rural.Rural;
import com.realestate.enums.PropertyType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
public class RuralDTO extends PropertyDTO {
    private AreaMeasurementDTO totalAreaRural;
    private AreaMeasurementDTO legalReserveArea;
    private PropertyType propertyType = PropertyType.RURAL;

    @JsonProperty("totalAreaRural")
    private void setTotalAreaRural(Double totalAreaAlqueirosGoianos) {
        if (totalAreaAlqueirosGoianos != null) {
            this.totalAreaRural = AreaMeasurementDTO.customBuilder()
                    .alqueiresGoianos(totalAreaAlqueirosGoianos)
                    .build();
        }
    }

    @JsonProperty("legalReserveArea")
    private void setLegalReserveArea(Double legalReserveAreaAlqueirosGoianos) {
        if (legalReserveAreaAlqueirosGoianos != null) {
            this.legalReserveArea = AreaMeasurementDTO.customBuilder()
                    .alqueiresGoianos(legalReserveAreaAlqueirosGoianos)
                    .build();
        }
    }

    public RuralDTO(AreaMeasurementDTO totalAreaRural, AreaMeasurementDTO legalReserveArea) {
        this.totalAreaRural = totalAreaRural;
        this.legalReserveArea = legalReserveArea;
    }


    public Rural toEntity() {
        if (totalAreaRural == null || legalReserveArea == null) {
            throw new IllegalArgumentException("Total area and legal reserve area must be provided");
        }

        return Rural.builder()
                .id(this.getId())
                .totalAreaRural(this.totalAreaRural.toEntity())
                .legalReserveArea(this.legalReserveArea.toEntity())
                .propertyType(this.propertyType)
                .build();
    }
}

