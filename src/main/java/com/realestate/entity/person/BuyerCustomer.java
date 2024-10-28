package com.realestate.entity.person;


import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


import java.time.LocalDate;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("BUYER")
public class BuyerCustomer extends Person{

    @ManyToOne
    @JoinColumn(name = "agent_id")
    private Agent responsibleAgent;

    private LocalDate firstContactDate;

}
