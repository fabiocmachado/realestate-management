package com.realestate.entity.person;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.realestate.entity.property.Property;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("SELLER")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Seller extends Person {

    @JsonBackReference
    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private List<Property> properties;

    public void addProperty(Property property) {
        properties.add(property);
        property.setSeller(this);
    }

    public void removeProperty(Property property) {
        properties.remove(property);
        property.setSeller(null);
    }

    @PreRemove
    private void validateDelete() {
        if (properties != null && !properties.isEmpty()) {
            throw new IllegalStateException(
                    "Não é possível excluir o vendedor enquanto houver imóveis vinculados a ele."
            );
        }
    }
}
