package com.realestate.dto;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CommercialAreaDTO extends CommercialDTO{
    private String hasWall;

    private String hasAsphalt;

    private Float totalArea;
}
