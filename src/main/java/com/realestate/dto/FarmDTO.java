package com.realestate.dto;

import com.realestate.enums.ConservationStatus;
import com.realestate.enums.EnergyType;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
public class FarmDTO extends RuralDTO {

    private String name;
    private Double formedArea;
    private String typeOfSoil;
    private String predominantPasture;
    private String otherPastures;
    private ConservationStatus pastureConservation;
    private Boolean hasSmoothWireFence;
    private String topography;
    private Boolean hasRocks;
    private String rivers;
    private String dams;
    private String mainHouse;
    private String staffHouse;
    private EnergyType energy;
    private Boolean hasOrchard;
    private String warehouse;
    private String accommodation;
    private String pens;
    private Boolean hasLandingStrip;
    private Integer herdSupport;
    private Float distanceOfGyn;
    private Float distanceOfCity;
    private Float distanceDirtRoad;
    private Integer pastures;
}