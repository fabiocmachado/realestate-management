package com.realestate.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "buyer_customers")
public class BuyerCustomer extends Person{

    @ManyToOne
    @JoinColumn(name = "agent_id")
    private RealEstateAgent responsibleAgent;

    private LocalDate firstContactDate;

}
