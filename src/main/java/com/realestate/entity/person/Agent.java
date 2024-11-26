package com.realestate.entity.person;

import com.realestate.entity.property.Property;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
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

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    @Column(name = "has_admin_permissions", nullable = false)
    private Boolean hasAdminPermissions = false;

    @OneToMany(mappedBy = "agent", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Property> prospectedProperties;

    @ManyToOne
    @JoinColumn(name = "responsible_buyer_id")
    private Buyer responsibleBuyer;

}

