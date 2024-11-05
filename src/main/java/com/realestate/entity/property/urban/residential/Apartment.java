package com.realestate.entity.property.urban.residential;

import com.realestate.enums.PropertyType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("APARTMENT")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Apartment extends Residential {

    @Column(name = "apartment_number")
    private String apartmentNumber;

    @Column(name = "apartment_floor")
    private Integer floor;

    @Column(name = "living_room")
    private Integer livingRoom;

    @Column(name = "condominium_fee")
    private Double condominiumFee;
    {
        setPropertyType(PropertyType.APARTMENT);
    }
}