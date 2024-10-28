package com.realestate.entity.property.urban.comercial;

import com.realestate.entity.property.urban.Urban;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@DiscriminatorValue("COMERCIAL")
public class CommercialBuilding extends Urban {
}
