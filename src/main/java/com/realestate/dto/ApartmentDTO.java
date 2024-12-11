package com.realestate.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ApartmentDTO extends ResidentialDTO {

    private String apartmentNumber;

    private Integer numberOfFloors;

    private Integer livingRoom;

    private Double condominiumFee;

    private Integer offices;

    private String nameOfBuilding;

    private Boolean hasPantry;

    private Integer toilet;

    private Boolean hasBarbecue;

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

    private Boolean hasGameRoom;

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

    private Boolean hasPrivateSwimmingPool;

    private Boolean hasExclusiveSauna;

    private Boolean hasMezzanine;
}
