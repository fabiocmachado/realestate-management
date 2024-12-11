package com.realestate.entity.property.urban.residential;

import com.realestate.entity.property.urban.Urban;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("URBAN_LAND")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class UrbanLand extends Residential {

    @Column(name = "hasWall")
    private Boolean hasWall;

    @Column(name = "asphalt")
    private Boolean hasAsphalt;
}
