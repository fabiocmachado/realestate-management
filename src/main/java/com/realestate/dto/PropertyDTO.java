package com.realestate.dto;

import com.realestate.enums.PropertyStatus;
import com.realestate.enums.PropertyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyDTO {
    private Long id;
    private String propertyCode;
    private PropertyType propertyType;
    private Integer price;
    private String street;
    private String block;
    private String lot;
    private String complement;
    private String number;
    private String city;
    private String state;
    private String placeOfKeys;
    private String orientation;
    private String description;
    private PropertyStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String propertyCategory;
    private Float usableArea;
    private Float privateArea;
    private Float totalArea;
    private Long agentId;
    private Long sellerId;
}