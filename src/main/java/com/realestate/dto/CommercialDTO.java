package com.realestate.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CommercialDTO extends PropertyDTO{

    private Integer yearsOfConstruction;

    private Boolean isRented;

    private Float rentalValue;

    private Boolean isInhabited;

    private Boolean hasAirConditioning;

    private String floorType;

    private Boolean hasSurveillanceCameras;

    private Double condominiumFee;

    private String nameOfBuilding;

    private float usableArea;

    private float privateArea;

    private Float totalArea;

    private Integer offices;

    private Integer suites;

    private String visitingTime;

    private Boolean mezzanine;

    private Boolean hasKitchen;

    private Integer garageSpaces;

    private Boolean garagesInRow;

    private Boolean hasSolarEnergy;
}
