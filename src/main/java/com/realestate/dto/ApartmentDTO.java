package com.realestate.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ApartmentDTO extends ResidentialDTO {

    private String apartmentNumber;

    private Integer floor;

    private Integer livingRoom;

    private Double condominiumFee;

    private Integer offices;

    private float usableArea;

    private float privateArea;

    private String nameOfBuilding;

    private Float totalArea;

    private Boolean hasPantry;

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

    private Boolean hasSauna;

    private Integer suites;

    private Boolean hasBars;

    private Boolean hasEmployeeRoom;

    private Boolean hasEmployeeBathroom;

    private Boolean hasGaragesInRow;

    private Integer numberOfBlocks;

    private Integer totalOfApartments;

    private Boolean hasPartyHall;

    private Boolean hasGamesRoom;

    private Boolean hasPlayground;

    private Boolean hasToyArea;

    private Boolean hasSportsCourt;

    private Boolean hasElectronicGate;

    private Boolean hasElectronicDoorman;

    private Boolean hasIntercom;

    private Boolean hasGourmetBalcony;

    private Integer elevator;

    private Boolean hasCompartment;

    private String visitingTime;

    private Boolean hasExclusiveSwimmingPool;

    private Boolean hasExclusiveSauna;

    private Boolean hasMezzanine;
}
