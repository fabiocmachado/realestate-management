package com.realestate.entity.property.urban.comercial;

import com.realestate.entity.property.urban.comercial.Commercial;
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
@DiscriminatorValue("COMMERCIAL_ROOM")
@SuperBuilder
public class CommercialRoom extends Commercial {

    @Column(name = "apartment_number")
    private String apartmentNumber;

    @Column(name = "number_of_floors")
    private Integer numberOfFloors;
}