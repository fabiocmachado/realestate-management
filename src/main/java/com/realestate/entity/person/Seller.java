package com.realestate.entity.person;

import com.realestate.entity.property.Property;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("SELLER")
public class Seller extends Person{

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private List<Property> properties;

}
