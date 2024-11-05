package com.realestate.entity.property.urban.residential;

import com.realestate.entity.property.Property;
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
@AllArgsConstructor
@SuperBuilder
public class Residential extends Property {

    @NotNull(message = "Número de quartos é obrigatório")
    @Min(value = 1, message = "Número de quartos deve ser no mínimo 1")
    @Column(name = "bedrooms", nullable = false)
    private Integer bedrooms;

    @NotNull(message = "Número de banheiros é obrigatório")
    @Min(value = 1, message = "Número de banheiros deve ser no mínimo 1")
    @Column(name = "bathrooms", nullable = false)
    private Integer bathrooms;

    @Column(name = "garage_spaces")
    private Integer garageSpaces;
    {
        setPropertyType(PropertyType.RESIDENTIAL);
    }
}