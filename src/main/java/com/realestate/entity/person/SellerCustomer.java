package com.realestate.entity.person;

import com.realestate.entity.property.Property;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@DiscriminatorValue("SELLER")
public class SellerCustomer extends Person{

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Property> properties;

}
