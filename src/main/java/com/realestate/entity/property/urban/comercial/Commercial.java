package com.realestate.entity.property.urban.comercial;

import com.realestate.entity.property.Property;
import com.realestate.entity.property.urban.Urban;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@DiscriminatorValue("COMMERCIAL")
@SuperBuilder
public class Commercial extends Urban {

    @Column(name = "years_of_construction")
    private Integer yearsOfConstruction;

    @Column(name = "rented")
    private Boolean isRented;

    @Column(name = "rental_value")
    private Float rentalValue;

    @Column(name = "inhabited")
    private Boolean isInhabited;

    @Column(name = "air_conditioning")
    private Boolean hasAirConditioning;

    @Column(name = "floor_type")
    private String floorType;

    @Column(name = "surveillance_cameras")
    private Boolean hasSurveillanceCameras;

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

    @Column(name = "suites")
    private Integer suites;

    @Column(name = "visiting_time")
    private String visitingTime;

    @Column(name = "mezzanine")
    private Boolean mezzanine;

    private Boolean hasKitchen;

    @Column(name = "garage_spaces")
    private Integer garageSpaces;

    @Column(name = "garages_in_row")
    private Boolean garagesInRow;

    @Column(name = "solar_energy")
    private Boolean hasSolarEnergy;

    @Column(name = "keyAvailable")
    private String keyAvailable;
}
