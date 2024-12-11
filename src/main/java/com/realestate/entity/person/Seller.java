package com.realestate.entity.person;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonManagedReference
    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Property> properties;

    @PreRemove
    private void validateDelete() {
        if (properties != null && !properties.isEmpty()) {
            throw new IllegalStateException(
                    "Não é possível excluir o vendedor enquanto houver imóveis vinculados a ele."
            );
        }
    }
}
