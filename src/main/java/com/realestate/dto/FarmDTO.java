package com.realestate.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.realestate.enums.ConservationStatus;
import com.realestate.enums.EnergyType;
import com.realestate.repository.SellerRepository;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
public class FarmDTO extends RuralDTO {

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotBlank
    @Size(max = 100)
    private String city;

    @NotBlank
    private String state;

    @NotNull
    private AreaMeasurementDTO formedArea;

    private String typeOfSoil;
    private String predominantPasture;
    private String otherPastures;
    private ConservationStatus pastureConservation;
    private Boolean smoothWireFence;
    private String topography;
    private Boolean rocks;
    private String rivers;
    private String dams;
    private String mainHouse;
    private String staffHouse;
    private EnergyType energy;
    private Boolean orchard;
    private String warehouse;
    private String accommodation;
    private String pens;
    private Boolean landingStrip;
    private Integer herdSupport;

    @NotNull
    @Min(0)
    private Float distanceOfGyn;

    @NotNull
    @Min(0)
    private Float distanceOfCity;

    @NotNull
    @Min(0)
    private Float distanceDirtRoad;

    @NotNull
    @Min(0)
    private Integer pastures;

    @JsonCreator
    public FarmDTO(
            @JsonProperty("totalArea") Double totalAreaAlqueirosGoianos,
            @JsonProperty("formedArea") Double formedAreaAlqueirosGoianos,
            @JsonProperty("legalReserveArea") Double legalReserveAreaAlqueirosGoianos, SellerRepository sellerRepository
    ) {
        super(
                new AreaMeasurementDTO(totalAreaAlqueirosGoianos),
                new AreaMeasurementDTO(legalReserveAreaAlqueirosGoianos)
        );
        this.formedArea = new AreaMeasurementDTO(formedAreaAlqueirosGoianos);
    }
}
