package com.realestate.dto;

import jakarta.persistence.Column;
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

    private Integer floors;

    private Integer livingRoom;

    private Double condominiumFee;

    private Integer offices;

    private float usableArea;

    private float privateArea;

    private String nameOfBuilding;

    private Float totalArea;

    private Boolean pantry;

    private Integer toilet;

    private Boolean hasBarbecueGrill;

    private Boolean hasSwimmingPool;

    private Integer yearsOfConstruction;

    private Boolean hasBalcony;

    private Boolean isRented;

    private Boolean isInhabited;

    private String floorType;

    private Boolean hasAirConditioning;

    private Boolean hasSurveillanceCameras;

    private Boolean hasCabinets;

    private Boolean hasLaundry;

    private Boolean sauna;
}
