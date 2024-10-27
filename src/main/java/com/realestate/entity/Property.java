package com.realestate.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@MappedSuperclass
public abstract class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PropertyType type;

    private Double price;
    private Double totalArea;
    private Double privateArea;
    private Double usableArea;
    private String address;
    private String description;

    @Enumerated(EnumType.STRING)
    private PropertyStatus status;

    private LocalDate registrationDate;

    @ManyToOne
    @JoinColumn(name = "seller id")
    private SellerCustomer sellerCustomer;

    @ElementCollection
    private List<String> photos;

}
