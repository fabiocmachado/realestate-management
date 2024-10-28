package com.realestate.enums;

import lombok.Getter;

@Getter
public enum UrbanType {
    HOUSE("Casa"),
    TOWNHOUSE("Sobrado"),
    APARTMENT("Apartamento Padrão"),
    STUDIO("Studio"),
    LOFT("Loft"),
    PENTHOUSE("Cobertura"),
    DUPLEX("Duplex");

    private final String description;

    UrbanType(String description) {
        this.description = description;
    }

}
