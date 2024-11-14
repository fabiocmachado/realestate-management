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

    @NotBlank(message = "Campo 'has wall' é obrigatório")
    private String hasWall;

    @NotBlank(message = "Campo 'has asphalt' é obrigatório")
    private String hasAsphalt;

    @NotBlank(message = "Campo 'key available' é obrigatório")
    private String keyAvailable;

    @NotNull(message = "Campo 'total area' é obrigatório")
    private Float totalArea;
}