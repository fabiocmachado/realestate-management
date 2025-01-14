package com.realestate.entity.property.urban.residential;

import com.realestate.entity.property.Property;
import com.realestate.entity.property.urban.Urban;
import com.realestate.enums.PropertyType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("RESIDENTIAL")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class Residential extends Urban {

    @Column(name = "bedrooms")
    private Integer bedrooms;

    @Column(name = "bathrooms")
    private Integer bathrooms;

    @Column(name = "kitchen")
    private Boolean hasKitchen;

    @Column(name = "garage_spaces")
    private Integer garageSpaces;

    @Column(name = "rental_value")
    private Float rentalValue;
}