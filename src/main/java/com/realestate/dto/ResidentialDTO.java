package com.realestate.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ResidentialDTO extends PropertyDTO {
    private Integer bedrooms;
    private Integer bathrooms;
    private Integer garageSpaces;
}
