package com.realestate.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
public class UrbanLandDTO extends ResidentialDTO {


    private Boolean hasWall;

    private Boolean hasAsphalt;

    private String keyAvailable;

    @NotNull(message = "Campo 'total area' é obrigatório")
    private Float totalArea;
}