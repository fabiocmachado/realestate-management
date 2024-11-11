package com.realestate.dto;

import com.realestate.entity.property.rural.Rural;
import com.realestate.enums.PropertyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
public class RuralDTO extends PropertyDTO {

    private AreaMeasurementDTO totalArea;
    private AreaMeasurementDTO legalReserveArea;
    private PropertyType propertyType = PropertyType.RURAL;

    public RuralDTO(AreaMeasurementDTO totalArea, AreaMeasurementDTO legalReserveArea) {
        this.totalArea = totalArea;
        this.legalReserveArea = legalReserveArea;
        this.propertyType = PropertyType.RURAL;
    }

    public Rural toEntity() {
        return Rural.builder()
                .id(this.getId())
                .totalArea(this.totalArea.toEntity())
                .legalReserveArea(this.legalReserveArea.toEntity())
                .propertyType(this.propertyType)
                .build();
    }

    public static RuralDTO fromEntity(Rural rural) {
        return RuralDTO.builder()
                .id(rural.getId())
                .totalArea(new AreaMeasurementDTO((double) rural.getTotalArea().getAlqueiresGoianos()))
                .legalReserveArea(new AreaMeasurementDTO((double) rural.getLegalReserveArea().getAlqueiresGoianos()))
                .propertyType(rural.getPropertyType())
                .build();
    }
}

