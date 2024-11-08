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

    @Column(name = "name_of_building")
    private String nameOfBuilding;

    @Column(name = "usable_area")
    private float usableArea;

    @Column(name = "private_area")
    private float privateArea;

    @Column(name = "total_area")
    private Float totalArea;

    @Column(name = "offices")
    private Integer offices;
}