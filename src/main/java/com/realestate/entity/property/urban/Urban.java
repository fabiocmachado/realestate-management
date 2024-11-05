package com.realestate.entity.property.urban;



import com.realestate.entity.property.Property;
import com.realestate.enums.PropertyType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@DiscriminatorValue("URBAN")
public class Urban extends Property {

    {
        setPropertyType(PropertyType.URBAN);
    }
}
