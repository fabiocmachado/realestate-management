package com.realestate.dto;

import com.realestate.enums.PropertyType;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class RuralDTO extends PropertyDTO {

    private Double totalAreaRural;

    private Double legalReserveArea;

    public RuralDTO() {
        super.setPropertyType(PropertyType.RURAL);
    }

    // Optional: Add validation if you want to ensure data integrity at the DTO level
    public void validateAreas() {
        if (totalAreaRural <= 0) {
            throw new IllegalStateException("Total area must be greater than zero");
        }

        if (legalReserveArea < 0) {
            throw new IllegalStateException("Legal reserve area cannot be negative");
        }

        if (legalReserveArea > totalAreaRural) {
            throw new IllegalStateException("Legal reserve area cannot exceed total area");
        }
    }
}