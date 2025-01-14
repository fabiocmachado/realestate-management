package com.realestate.entity.property.rural;

import com.realestate.enums.ConservationStatus;
import com.realestate.enums.EnergyType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("FARM")
@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Farm extends Rural {

    @Size(max = 100)
    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "type_of_soil", length = 50)
    private String typeOfSoil;

    @Column(name = "predominant_pasture", length = 50)
    private String predominantPasture;

    @Column(name = "other_pastures", length = 200)
    private String otherPastures;

    @Enumerated(EnumType.STRING)
    @Column(name = "pasture_conservation")
    private ConservationStatus pastureConservation;

    @Column(name = "smooth_wire_fence")
    private Boolean hasSmoothWireFence;

    @Column(name = "topography", length = 50)
    private String topography;

    @Column(name = "rocks")
    private Boolean hasRocks;

    @Column(name = "rivers", length = 50)
    private String rivers;

    @Column(name = "dams", length = 50)
    private String dams;

    @Column(name = "main_house", length = 100)
    private String mainHouse;

    @Column(name = "staff_house", length = 100)
    private String staffHouse;

    @Enumerated(EnumType.STRING)
    @Column(name = "energy")
    private EnergyType energy;

    @Column(name = "orchard")
    private Boolean hasOrchard;

    @Column(name = "warehouse", length = 100)
    private String warehouse;

    @Column(name = "accommodation", length = 100)
    private String accommodation;

    @Column(name = "pens", length = 100)
    private String pens;

    @Column(name = "landing_strip")
    private Boolean hasLandingStrip;

    @Column(name = "herd_support")
    private Integer herdSupport;

    @Min(0)
    @Column(name = "distance_of_gyn")
    private Float distanceOfGyn;

    @Min(0)
    @Column(name = "distance_of_city")
    private Float distanceOfCity;

    @Min(0)
    @Column(name = "distance_dirt_road")
    private Float distanceDirtRoad;

    @Column(name = "formedArea")
    private Double formedArea;

    @Min(0)
    @Column(name = "pastures")
    private Integer pastures;

}