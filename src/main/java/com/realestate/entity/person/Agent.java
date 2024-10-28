package com.realestate.entity.person;

import com.realestate.entity.property.Property;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
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
    private List<Property> prospectedProperties;

    public Agent(Long id, String name, String cpf, String rg, String email, String phone, String address, String licenseNumber, LocalDate hiringDate, List<Property> prospectedProperties) {
        this.setId(id);
        this.setName(name);
        this.setCpf(cpf);
        this.setRg(rg);
        this.setEmail(email);
        this.setPhone(phone);
        this.setAddress(address);
        this.licenseNumber = licenseNumber;
        this.hiringDate = hiringDate;
        this.prospectedProperties = prospectedProperties;
    }
}
