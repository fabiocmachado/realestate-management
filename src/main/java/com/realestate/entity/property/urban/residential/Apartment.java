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
    private Boolean sauna;

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
    private Boolean pantry;

    @Column(name = "suites")
    private Integer suites;

    @Column(name = "bars")
    private Boolean bars;

    @Column(name = "employee_room")
    private Boolean employeeRoom;

    @Column(name = "employee_bathroom")
    private Boolean employeeBathroom;

    @Column(name = "garages_in_row")
    private Boolean garagesInRow;

    @Column(name = "number_of_Blocks")
    private Integer numberOfBlocks;

    @Column(name = "total_of_apartments")
    private Boolean totalOfApartments;

    @Column(name = "party_hall")
    private Boolean partyHall;

    @Column(name = "games_room")
    private Boolean gamesRoom;

    @Column(name = "playground")
    private Boolean playground;

    @Column(name = "toy_area")
    private Boolean toyArea;

    @Column(name = "sports_court")
    private Boolean sportsCourt;

    @Column(name = "electronic_gate")
    private Boolean hasElectronicGate;

    @Column(name = "electronic_doorman")
    private Boolean electronicDoorman;

    @Column(name = "intercom")
    private Boolean intercom;

    @Column(name = "gourmet_balcony")
    private Integer gourmetBalcony;

    @Column(name = "elevator")
    private Integer elevator;

    @Column(name = "compartment")
    private Boolean compartment;

    @Column(name = "visiting_time")
    private String visitingTime;

    @Column(name = "exclusive_swimming_pool")
    private Boolean exclusiveSwimmingPool;

    @Column(name = "exclusive_saunsa")
    private Boolean exclusiveSauna;

    @Column(name = "mezzanine")
    private Boolean mezzanine;


}