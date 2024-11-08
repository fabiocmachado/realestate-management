package com.realestate.entity.property;

import com.realestate.entity.person.Agent;
import com.realestate.entity.person.Seller;
import com.realestate.enums.PropertyType;
import com.realestate.enums.PropertyStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.realestate.dto.PropertyDTO.generateUniquePropertyCode;


@Entity
@Table(name = "properties",
        indexes = {
                @Index(name = "idx_property_status", columnList = "status"),
                @Index(name = "idx_property_price", columnList = "price"),
        }
)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "property_category", discriminatorType = DiscriminatorType.STRING)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    private String propertyCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "property_type", nullable = false)
    private PropertyType propertyType;

    @NotNull(message = "Preço é obrigatório")
    @Positive(message = "Preço deve ser positivo")
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
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @NotNull(message = "Vendedor é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
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
