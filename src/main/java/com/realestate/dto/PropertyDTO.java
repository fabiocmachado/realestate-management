package com.realestate.dto;

import com.realestate.enums.PropertyStatus;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@SuperBuilder
@NoArgsConstructor
public class PropertyDTO {

    private Long id;

    private String propertyCode;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    @Digits(integer = 10, fraction = 2, message = "Price should have up to 10 digits with up to 2 decimal places")
    private BigDecimal price;

    @NotBlank(message = "Address is required")
    @Size(max = 500, message = "Address must have a maximum of 500 characters")
    private String address;

    @Size(max = 2000, message = "Description must have a maximum of 2000 characters")
    private String description;

    @NotNull(message = "Property status is required")
    private PropertyStatus status;

    @NotNull(message = "Seller ID is required")
    private Long sellerId;

    @NotNull(message = "Agente é obrigatório")
    private Long agentId;

    private String propertyCategory;

    private static long counter = 0;

    public static String generateUniquePropertyCode() {
        counter++;
        return String.format("PROP-%06d", counter);
    }
}
