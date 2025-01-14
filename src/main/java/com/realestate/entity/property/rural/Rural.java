package com.realestate.entity.property.rural;

import com.realestate.entity.property.Property;
import com.realestate.enums.PropertyType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("RURAL")
@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Rural extends Property {

    @NotNull
    @Column(name = "total_area_alqueires")
    private Double totalAreaRural;

    @Column(name = "legal_reserve_alqueires")
    private Double legalReserveArea;

    {
        setPropertyType(PropertyType.RURAL);
    }

    @PrePersist
    @PreUpdate
    private void validateAreas() {
        if (totalAreaRural <= 0) {
            throw new IllegalStateException("Total area must be greater than zero");
        }

        if (legalReserveArea < 0) {
            throw new IllegalStateException("Legal reserve area cannot be negative");
        }

        if (legalReserveArea > totalAreaRural) {
            throw new IllegalStateException("Legal reserve area cannot exceed total area");
        }
    }
}