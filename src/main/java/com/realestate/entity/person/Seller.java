package com.realestate.entity.person;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Seller extends Person{

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Property> properties;

}
