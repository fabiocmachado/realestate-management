package com.realestate.entity.person;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("BUYER")
public class Buyer extends Person{

    @ManyToOne
    @JoinColumn(name = "agent_id",referencedColumnName = "id")
    private Agent responsibleAgent;

    private LocalDate firstContactDate;

}
