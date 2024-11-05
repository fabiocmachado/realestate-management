package com.realestate.enums;

import lombok.Getter;

@Getter
public enum PropertyStatus {
    AVAILABLE("Disponível"),
    SOLD("Vendido"),
    RESERVED("Reservado"),
    UNDER_NEGOTIATION("Em Negociação"),
    INACTIVE("Inativo");

    private final String description;

    PropertyStatus(String description) {
        this.description = description;
    }

}