package com.realestate.entity.property;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.realestate.dto.PropertyDTO.generateUniquePropertyCode;

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
    private Long id;

    @Column(name = "property_code", unique = true, nullable = false, updatable = false)
    private String propertyCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "property_type", nullable = false)
    private PropertyType propertyType;

    @NotNull(message = "Preço é obrigatório")
    @Positive(message = "Preço deve ser positivo")
    @Digits(integer = 10, fraction = 2, message = "Price should have up to 10 digits with up to 2 decimal places")
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal price;

    @NotBlank(message = "Endereço é obrigatório")
    @Size(max = 500, message = "Endereço deve ter no máximo 500 caracteres")
    @Column(nullable = false, length = 500)
    private String address;

    @Size(max = 2000, message = "Descrição deve ter no máximo 2000 caracteres")
    @Column(length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status do imóvel é obrigatório")
    @Column(name = "status", nullable = false)
    private PropertyStatus status;

    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_id")
    private Agent agent;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prospected_by_id",referencedColumnName = "id")
    private Agent prospectedBy;

    @PrePersist
    protected void onCreate() {
        if (this.propertyCode == null) {
            this.propertyCode = generateUniquePropertyCode();
        }
        if (this.isActive == null) {
            this.isActive = true;
        }
    }

    public boolean isAvailable() {
        return Boolean.TRUE.equals(isActive) && status == PropertyStatus.AVAILABLE;
    }
}
