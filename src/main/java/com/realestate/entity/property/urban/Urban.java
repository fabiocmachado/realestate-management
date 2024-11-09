package com.realestate.entity.property.urban;



import com.realestate.entity.property.Property;
import com.realestate.enums.PropertyType;
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
@DiscriminatorValue("URBAN")
@SuperBuilder
public class Urban extends Property {

    {
        setPropertyType(PropertyType.URBAN);
    }
}
