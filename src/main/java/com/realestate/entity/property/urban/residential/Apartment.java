package com.realestate.entity.property.urban.residential;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("APARTMENT")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class Apartment extends Residential {

    @Column(name = "apartment_number")
    private String apartmentNumber;

    @Column(name = "apartment_floor")
    private Integer floor;

    @Column(name = "living_room")
    private Integer livingRoom;

    @Column(name = "toilet")
    private Integer toilet;

    @Column(name = "barbecue_grill")
    private Boolean hasBarbecueGrill;

    @Column(name = "swimming_pool")
    private Boolean hasSwimmingPool;

    @Column(name = "years_of_construction")
    private Integer yearsOfConstruction;

    @Column(name = "balcony")
    private Boolean hasBalcony;

    @Column(name = "rented")
    private Boolean isRented;

    @Column(name = "inhabited")
    private Boolean isInhabited;

    @Column(name = "floor_type")
    private String floorType;

    @Column(name = "air_conditioning")
    private Boolean hasAirConditioning;

    @Column(name = "surveillance_cameras")
    private Boolean hasSurveillanceCameras;

    @Column(name = "cabinets")
    private Boolean hasCabinets;

    @Column(name = "laundry")
    private Boolean hasLaundry;

    @Column(name = "sauna")
    private Boolean hasSauna;

    @Column(name = "condominium_fee")
    private Double condominiumFee;

    @Column(name = "name_of_building")
    private String nameOfBuilding;

    @Column(name = "usable_area")
    private float usableArea;

    @Column(name = "private_area")
    private float privateArea;

    @Column(name = "total_area")
    private Float totalArea;

    @Column(name = "offices")
    private Integer offices;

    @Column(name = "pantry")
    private Boolean hasPantry;

    @Column(name = "suites")
    private Integer suites;

    @Column(name = "bars")
    private Boolean hasBars;

    @Column(name = "employee_room")
    private Boolean hasEmployeeRoom;

    @Column(name = "employee_bathroom")
    private Boolean hasEmployeeBathroom;

    @Column(name = "garages_in_row")
    private Boolean hasGaragesInRow;

    @Column(name = "number_of_Blocks")
    private Integer numberOfBlocks;

    @Column(name = "total_of_apartments")
    private Integer totalOfApartments;

    @Column(name = "party_hall")
    private Boolean hasPartyHall;

    @Column(name = "games_room")
    private Boolean hasGamesRoom;

    @Column(name = "playground")
    private Boolean hasPlayground;

    @Column(name = "toy_area")
    private Boolean hasToyArea;

    @Column(name = "sports_court")
    private Boolean hasSportsCourt;

    @Column(name = "electronic_gate")
    private Boolean hasElectronicGate;

    @Column(name = "electronic_doorman")
    private Boolean hasElectronicDoorman;

    @Column(name = "intercom")
    private Boolean hasIntercom;

    @Column(name = "gourmet_balcony")
    private Boolean hasGourmetBalcony;

    @Column(name = "elevator")
    private Integer elevator;

    @Column(name = "compartment")
    private Boolean hasCompartment;

    @Column(name = "visiting_time")
    private String visitingTime;

    @Column(name = "exclusive_swimming_pool")
    private Boolean hasExclusiveSwimmingPool;

    @Column(name = "exclusive_saunsa")
    private Boolean hasExclusiveSauna;

    @Column(name = "mezzanine")
    private Boolean hasMezzanine;


}