package com.realestate.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.realestate.enums.ConservationStatus;
import com.realestate.enums.EnergyType;
import com.realestate.repository.SellerRepository;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
public class FarmDTO extends RuralDTO {

    private String name;

    @NotNull
    private AreaMeasurementDTO formedArea;

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

    @JsonCreator
    public FarmDTO(
            @JsonProperty("totalArea") Double totalAreaAlqueirosGoianos,
            @JsonProperty("formedArea") Double formedAreaAlqueirosGoianos,
            @JsonProperty("legalReserveArea") Double legalReserveAreaAlqueirosGoianos
    ) {
        super(
                new AreaMeasurementDTO(totalAreaAlqueirosGoianos),
                new AreaMeasurementDTO(legalReserveAreaAlqueirosGoianos)
        );
        this.formedArea = new AreaMeasurementDTO(formedAreaAlqueirosGoianos);
    }

    @JsonProperty("formedArea")
    private void setFormedArea(Double formedAreaAlqueirosGoianos) {
        this.formedArea = new AreaMeasurementDTO(formedAreaAlqueirosGoianos);
    }
}
