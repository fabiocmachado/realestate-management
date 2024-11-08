package com.realestate.entity.property.rural;

import com.realestate.enums.ConservationStatus;
import com.realestate.enums.EnergyType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("FARM")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Farm extends Rural {

    @NotBlank
    @Size(max = 100)
    @Column(name = "name", length = 100)
    private String name;

    @NotBlank
    @Size(max = 100)
    @Column(name = "city", length = 100)
    private String city;

    @NotBlank
    @Size(max = 2)
    @Column(name = "state", length = 2)
    private String state;

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
    private Boolean smoothWireFence;

    @Column(name = "topography", length = 50)
    private String topography;

    @Column(name = "rocks")
    private Boolean rocks;

    @Column(name = "rivers", length = 50)
    private String rivers;

    @Column(name = "dams", length = 50)
    private String dams;

    @Column(name = "main_house", length = 50)
    private String mainHouse;

    @Column(name = "staff_house", length = 50)
    private String staffHouse;

    @Enumerated(EnumType.STRING)
    @Column(name = "energy")
    private EnergyType energy;

    @Column(name = "orchard")
    private Boolean orchard;

    @Column(name = "warehouse", length = 50)
    private String warehouse;

    @Column(name = "accommodation", length = 50)
    private String accommodation;

    @Column(name = "pens", length = 50)
    private String pens;

    @Column(name = "landing_strip")
    private Boolean landingStrip;

    @Column(name = "herd_support")
    private Integer herdSupport;

    @NotNull
    @Min(0)
    @Column(name = "distance_of_gyn")
    private Float distanceOfGyn;

    @NotNull
    @Min(0)
    @Column(name = "distance_of_city")
    private Float distanceOfCity;

    @NotNull
    @Min(0)
    @Column(name = "distance_dirt_road")
    private Float distanceDirtRoad;

    @NotNull
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "hectares", column = @Column(name = "formed_area_hectares")),
            @AttributeOverride(name = "alqueiresGoianos", column = @Column(name = "formed_area_alqueires"))
    })
    private AreaMeasurement formedArea;

    @NotNull
    @Min(0)
    @Column(name = "pastures")
    private Integer pastures;

}