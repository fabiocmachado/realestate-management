package com.realestate.entity.person;

import com.realestate.entity.property.Property;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@DiscriminatorValue("AGENT")
public class Agent extends Person{

    private String licenseNumber;

    private LocalDate hiringDate;

    @OneToMany(mappedBy = "prospectedBy", cascade = CascadeType.ALL)
    private List<Property> prospectedProperties;


}
