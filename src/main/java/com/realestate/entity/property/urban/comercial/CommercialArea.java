package com.realestate.entity.property.urban.comercial;

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
@DiscriminatorValue("COMMERCIAL_AREA")
@SuperBuilder
public class CommercialArea extends Commercial{

    @Column(name = "wall")
    private Boolean hasWall;

    @Column(name = "asphalt")
    private Boolean hasAsphalt;

    @Column(name = "total_area")
    private Float totalArea;

}
