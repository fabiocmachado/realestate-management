package com.realestate.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ApartmentDTO extends ResidentialDTO {

    private String apartmentNumber;

    @Min(value = 1, message = "The floor number must be at least 1")
    private Integer floors;

    @Min(value = 1, message = "Minimum of 1 living room is required")
    private Integer livingRoom;

    @Positive(message = "Condominium fee must be positive")
    private Double condominiumFee;

    private Integer offices;

    private float usableArea;

    private float privateArea;

    private String nameOfBuilding;
}
