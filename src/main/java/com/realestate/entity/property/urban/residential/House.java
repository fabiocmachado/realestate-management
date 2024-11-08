package com.realestate.entity.property.urban.residential;

import com.realestate.enums.PropertyType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("HOUSE")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class House extends Residential {



    @Column(name = "suites")
    private Integer suites;

    @Column(name = "living_room")
    private Integer livingRoom;

    @Column(name = "offices")
    private Integer offices;

    @Column(name = "laundry")
    private Boolean hasLaundry;

    @Column(name = "sauna")
    private Boolean sauna;

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

    @Column(name = "Kennel")
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

    @Column(name = "barbecue_grill")
    private Boolean hasBarbecueGrill;

    @Column(name = "toilet")
    private Integer toilet;

    @Column(name = "solar_energy")
    private Boolean hasSolarEnergy;

    @Column(name = "condominium_fee")
    private Double condominiumFee;

    @Column(name = "name_of_gated_community")
    private String nameOfGatedCommunity;

    @Column(name = "usable_area")
    private float usableArea;

    @Column(name = "private_area")
    private float privateArea;

    @Column(name = "total_area")
    private Float totalArea;

    @Column(name = "pantry")
    private Boolean pantry;

}
