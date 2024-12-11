package com.realestate.entity.property.urban.residential;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("HOUSE")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class House extends Residential {

    @Column(name = "suites")
    private Integer suites;

    @Column(name = "living_room")
    private Integer livingRoom;

    @Column(name = "offices")
    private Integer offices;

    @Column(name = "laundry_room")
    private Boolean hasLaundryRoom;

    @Column(name = "sauna")
    private Boolean hasSauna;

    @Column(name = "cabinets")
    private Boolean hasCabinets;

    @Column(name = "electronic_gate")
    private Boolean hasElectronicGate;

    @Column(name = "surveillance_cameras")
    private Boolean hasSurveillanceCameras;

    @Column(name = "garden")
    private Boolean hasGarden;

    @Column(name = "air_conditioning")
    private Boolean hasAirConditioning;

    @Column(name = "kennel")
    private Boolean hasKennel;

    @Column(name = "floor_type")
    private String floorType;

    @Column(name = "inhabited")
    private Boolean isInhabited;

    @Column(name = "rented")
    private Boolean isRented;

    @Column(name = "balcony")
    private Boolean hasBalcony;

    @Column(name = "years_of_construction")
    private Integer yearsOfConstruction;

    @Column(name = "yard")
    private Boolean hasYard;

    @Column(name = "swimming_pool")
    private Boolean hasSwimmingPool;

    @Column(name = "barbecue")
    private Boolean hasBarbecue;

    @Column(name = "toilet")
    private Integer toilet;

    @Column(name = "solar_energy")
    private Boolean hasSolarEnergy;

    @Column(name = "condominium_fee")
    private Integer condominiumFee;

    @Column(name = "name_of_gated_community")
    private String nameOfGatedCommunity;

    @Column(name = "pantry")
    private Boolean hasPantry;

}
