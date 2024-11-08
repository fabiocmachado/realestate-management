package com.realestate.entity.property.rural;

import com.realestate.entity.property.Property;
import com.realestate.enums.PropertyType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("RURAL")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Rural extends Property {
    @NotNull
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "hectares", column = @Column(name = "total_area_hectares")),
            @AttributeOverride(name = "alqueiresGoianos", column = @Column(name = "total_area_alqueires"))
    })
    private AreaMeasurement totalArea;

    @NotNull
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "hectares", column = @Column(name = "legal_reserve_hectares")),
            @AttributeOverride(name = "alqueiresGoianos", column = @Column(name = "legal_reserve_alqueires"))
    })
    private AreaMeasurement legalReserveArea;

    {
        setPropertyType(PropertyType.RURAL);
    }
//    @PrePersist
//    @PreUpdate
//    private void validateAreas() {
//        if (getTotalArea() == null || getLegalReserveArea() == null) {
//            throw new IllegalStateException("Total area and legal reserve area must be set");
//        }
//
//        double totalAreaHectares = getTotalArea().getHectares();
//        double legalReserveAreaHectares = getLegalReserveArea().getHectares();
//
//        if (legalReserveAreaHectares > totalAreaHectares) {
//            throw new IllegalStateException("Legal reserve area cannot exceed total area");
//        }
//    }
}
