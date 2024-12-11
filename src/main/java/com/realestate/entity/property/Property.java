package com.realestate.entity.property;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.realestate.entity.person.Agent;
import com.realestate.entity.person.Seller;
import com.realestate.enums.PropertyStatus;
import com.realestate.enums.PropertyType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "properties", indexes = {
        @Index(name = "idx_property_status", columnList = "status"),
        @Index(name = "idx_property_price", columnList = "price")
})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "property_category", discriminatorType = DiscriminatorType.STRING)
public abstract class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "property_code", unique = true, nullable = false, updatable = false)
    private String propertyCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "property_type", nullable = false)
    private PropertyType propertyType;

    @NotNull(message = "Preço é obrigatório")
    @Positive(message = "Preço deve ser positivo")
    @Column(nullable = false, precision = 19, scale = 2)
    private Integer price;

    @Column(name = "street")
    private String street;

    @Column(name = "block")
    private String block;

    @Column(name = "lot")
    private String lot;

    @Column(name = "complement")
    private String complement;

    @Column(name = "number")
    private String number;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "place_of_keys")
    private String placeOfKeys;

    @Column(name = "orientation")
    private String orientation;

    @Size(max = 2000, message = "Descrição deve ter no máximo 2000 caracteres")
    @Column(length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status do imóvel é obrigatório")
    @Column(name = "status", nullable = false)
    private PropertyStatus status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_id")
    private Agent agent;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @Column(name = "property_category", insertable = false, updatable = false)
    private String propertyCategory;

    @Column(name = "usable_area")
    private Float usableArea;

    @Column(name = "private_area")
    private Float privateArea;

    @Column(name = "total_area")
    private Float totalArea;

    private String generateUniquePropertyCode() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    @PrePersist
    protected void onCreate() {
        if (this.propertyCode == null) {
            this.propertyCode = generateUniquePropertyCode();
        }
        if (this.status == null) {
            this.status = PropertyStatus.AVAILABLE;
        }
    }

}
