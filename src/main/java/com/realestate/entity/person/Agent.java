package com.realestate.entity.person;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.realestate.entity.property.Property;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@DiscriminatorValue("AGENT")
public class Agent extends Person {

    private String licenseNumber;

    @OneToMany(mappedBy = "prospectedBy", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Property> prospectedProperties = new ArrayList<>();

    @OneToMany(mappedBy = "responsibleAgent")
    @JsonBackReference
    private List<Buyer> responsibleBuyers;

    public Agent(Long id, String name, String cpf, String rg, String email,
                 String phone, String address, String licenseNumber, List<Property> prospectedProperties) {
        super(id, name, cpf, rg, email, phone, address, LocalDate.now());
        this.licenseNumber = licenseNumber;
        this.prospectedProperties = prospectedProperties != null ?
                prospectedProperties : new ArrayList<>();
    }
}
