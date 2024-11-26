package com.realestate.entity.person;

import com.realestate.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "persons")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "person_type", discriminatorType = DiscriminatorType.STRING)
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Size(min = 11, max = 11, message = "O CPF deve conter 11 números.")
    private String cpf;

    private String rg;
    private String email;
    @Getter
    private String password;
    private String phone;
    private String address;

    @Column(name = "registration_date", nullable = false, updatable = false)
    private LocalDate registrationDate = LocalDate.now();

    @Enumerated(EnumType.STRING)
    private UserRole role;

}
