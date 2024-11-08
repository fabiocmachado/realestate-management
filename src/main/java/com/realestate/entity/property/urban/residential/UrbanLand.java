package com.realestate.entity.property.urban.residential;

import com.realestate.entity.property.Property;
import com.realestate.entity.property.urban.Urban;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("Urban_land")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UrbanLand extends Urban {

    @Column(name = "wall")
    private String hasWall;

    @Column(name = "asphalt")
    private String hasAsphalt;

    @Column(name = "total_area")
    private Float totalArea;

    @Column(name = "keyAvailable")
    private String keyAvailable;
}
