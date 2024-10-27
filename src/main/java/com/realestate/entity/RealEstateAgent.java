package com.realestate.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "real_estate_agents")
public class RealEstateAgent extends Person{

    private String licenseNumber;

    private LocalDate hiringDate;

    @OneToMany(mappedBy = "prospectedBy", cascade = CascadeType.ALL)
    private List<Property> prospectedProperties;


}
