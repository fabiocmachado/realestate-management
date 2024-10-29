package com.realestate.entity.person;

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
    private LocalDate hiringDate;

    @OneToMany(mappedBy = "prospectedBy", cascade = CascadeType.ALL)
    private List<Property> prospectedProperties = new ArrayList<>();


    public Agent(Long id, String name, String cpf, String rg, String email,
                 String phone, String address, String licenseNumber,
                 LocalDate hiringDate, List<Property> prospectedProperties) {
        super(id, name, cpf, rg, email, phone, address, LocalDate.now());
        this.licenseNumber = licenseNumber;
        this.hiringDate = hiringDate;
        this.prospectedProperties = prospectedProperties != null ?
                prospectedProperties : new ArrayList<>();
    }
}
