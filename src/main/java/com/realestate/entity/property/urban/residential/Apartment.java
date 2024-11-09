package com.realestate.entity.property.urban.residential;

import com.realestate.enums.PropertyType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
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
}