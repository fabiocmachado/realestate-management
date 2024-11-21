package com.realestate.entity.property.urban.comercial;

import com.realestate.entity.property.urban.Urban;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@DiscriminatorValue("COMMERCIAL_BUILDING")
public class CommercialBuilding extends Commercial {
}
