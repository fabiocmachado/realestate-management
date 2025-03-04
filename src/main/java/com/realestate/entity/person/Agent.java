package com.realestate.entity.person;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.realestate.entity.property.Property;
import com.realestate.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("AGENT")
public class Agent extends Person {

    @Column(name = "license_number", unique = true)
    private String licenseNumber;

    @JsonBackReference
    @OneToMany(mappedBy = "agent", cascade = CascadeType.ALL)
    private List<Property> prospectedProperties;

    private String password;

    @Getter
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Role is required")
    private UserRole role;

    @PreRemove
    private void validateDelete() {
        if (!prospectedProperties.isEmpty()) {
            throw new IllegalStateException("Não é possível excluir um agente com propriedades vinculadas");
        }
    }
}

