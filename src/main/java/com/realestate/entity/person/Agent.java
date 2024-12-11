package com.realestate.entity.person;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.realestate.entity.property.Property;
import com.realestate.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("AGENT")
public class Agent extends Person {

    @Column(name = "license_number", nullable = false, unique = true)
    private String licenseNumber;

    @JsonBackReference
    @OneToMany(mappedBy = "agent", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Set<Property> prospectedProperties = new HashSet<>();

    @NotNull(message = "Password is required")
    private String password;

    @Getter
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Role is required")
    private UserRole role;

    @Column(name = "has_admin_permissions", nullable = false)
    private Boolean hasAdminPermissions;

    @PreRemove
    private void validateDelete() {
        if (!prospectedProperties.isEmpty()) {
            throw new IllegalStateException("Não é possível excluir um agente com propriedades vinculadas");
        }
    }
}

