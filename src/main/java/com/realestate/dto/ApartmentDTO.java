package com.realestate.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
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

    private Integer suites;

    private Boolean bars;

    private Boolean employeeRoom;

    private Boolean employeeBathroom;

    private Boolean garagesInRow;

    private Integer numberOfBlocks;

    private Boolean totalOfApartments;

    private Boolean partyHall;

    private Boolean gamesRoom;

    private Boolean playground;

    private Boolean toyArea;

    private Boolean sportsCourt;

    private Boolean hasElectronicGate;

    private Boolean electronicDoorman;

    private Boolean intercom;

    private Integer gourmetBalcony;

    private Integer elevator;

    private Boolean compartment;

    private String visitingTime;

    private Boolean exclusiveSwimmingPool;

    private Boolean exclusiveSauna;

    private Boolean mezzanine;
}
