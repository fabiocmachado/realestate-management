package com.realestate.entity.person;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("BUYER")
public class Buyer extends Person{

    @ManyToOne
    @JoinColumn(name = "agent_id")
    private Agent responsibleAgent;

    private LocalDate firstContactDate;

}
