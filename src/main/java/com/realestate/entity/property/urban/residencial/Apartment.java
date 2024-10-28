package com.realestate.entity.property.urban.residencial;

import com.realestate.entity.property.urban.Urban;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("APARTMENT")
public class Apartment extends Urban {

    private String nameOfBuilding;
    private String numberOfApartment;
    private int bedrooms;
    private int bathrooms;
    private int livingRooms;
    private int offices;
    private int monthlyCondominiumFee;
}
