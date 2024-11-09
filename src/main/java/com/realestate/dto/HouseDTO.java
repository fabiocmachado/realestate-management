package com.realestate.dto;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class HouseDTO extends ResidentialDTO {

    private Integer suites;

    private Integer livingRoom;

    private Integer offices;

    private Boolean hasLaundry;

    private Boolean sauna;

    private Boolean hasCabinets;

    private Boolean hasElectronicGate;

    private Boolean hasSurveillanceCameras;

    private Boolean hasGarden;

    private Boolean hasAirConditioning;

    private Boolean hasKennel;

    private String floorType;

    private Boolean isInhabited;

    private Boolean isRented;

    private Boolean hasBalcony;

    private Integer yearsOfConstruction;

    private Boolean hasYard;

    private Boolean hasSwimmingPool;

    private Boolean hasBarbecueGrill;

    private Integer toilet;

    private Boolean hasSolarEnergy;

    private Double condominiumFee;

    private String nameOfGatedCommunity;

    private float usableArea;

    private float privateArea;

    private Float totalArea;

    private Boolean pantry;

}