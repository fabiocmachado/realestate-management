package com.realestate.entity.property.urban.comercial;

import com.realestate.entity.property.urban.comercial.Commercial;
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
}