package com.realestate.entity.property.urban.comercial;

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
@DiscriminatorValue("WAREHOUSE")
@SuperBuilder
public class Warehouse extends Commercial{
}
