package com.realestate.entity.property.rural;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@DiscriminatorValue("COUNTRY_HOUSE")
@SuperBuilder
public class CountryHouse extends Farm{
}
