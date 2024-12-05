package com.realestate.mapper;

import com.realestate.dto.AreaMeasurementDTO;
import com.realestate.dto.CountryHouseDTO;
import com.realestate.entity.person.Agent;
import com.realestate.entity.person.Seller;
import com.realestate.entity.property.rural.CountryHouse;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CountryHouseMapper {

    @Mapping(target = "name", source = "entity.name")
    @Mapping(target = "city", source = "entity.city")
    @Mapping(target = "state", source = "entity.state")
    @Mapping(target = "formedArea", source = "entity.formedArea.alqueiresGoianos")
    @Mapping(target = "typeOfSoil", source = "entity.typeOfSoil")
    @Mapping(target = "predominantPasture", source = "entity.predominantPasture")
    @Mapping(target = "otherPastures", source = "entity.otherPastures")
    @Mapping(target = "pastureConservation", source = "entity.pastureConservation")
    @Mapping(target = "hasSmoothWireFence", source = "entity.hasSmoothWireFence")
    @Mapping(target = "topography", source = "entity.topography")
    @Mapping(target = "hasRocks", source = "entity.hasRocks")
    @Mapping(target = "rivers", source = "entity.rivers")
    @Mapping(target = "dams", source = "entity.dams")
    @Mapping(target = "mainHouse", source = "entity.mainHouse")
    @Mapping(target = "staffHouse", source = "entity.staffHouse")
    @Mapping(target = "energy", source = "entity.energy")
    @Mapping(target = "hasOrchard", source = "entity.hasOrchard")
    @Mapping(target = "warehouse", source = "entity.warehouse")
    @Mapping(target = "accommodation", source = "entity.accommodation")
    @Mapping(target = "pens", source = "entity.pens")
    @Mapping(target = "hasLandingStrip", source = "entity.hasLandingStrip")
    @Mapping(target = "herdSupport", source = "entity.herdSupport")
    @Mapping(target = "distanceOfGyn", source = "entity.distanceOfGyn")
    @Mapping(target = "distanceOfCity", source = "entity.distanceOfCity")
    @Mapping(target = "distanceDirtRoad", source = "entity.distanceDirtRoad")
    @Mapping(target = "pastures", source = "entity.pastures")
    @Mapping(target = "address", source = "entity.address")
    @Mapping(target = "seller", source = "entity.seller")
    @Mapping(target = "agent", source = "entity.agent")
    @Mapping(target = "propertyCategory", constant = "COUNTRY_HOUSE")
    CountryHouseDTO toDTO(CountryHouse entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "countryHouseDTO.name")
    @Mapping(target = "city", source = "countryHouseDTO.city")
    @Mapping(target = "state", source = "countryHouseDTO.state")
    @Mapping(target = "formedArea", source = "countryHouseDTO.formedArea")
    @Mapping(target = "typeOfSoil", source = "countryHouseDTO.typeOfSoil")
    @Mapping(target = "predominantPasture", source = "countryHouseDTO.predominantPasture")
    @Mapping(target = "otherPastures", source = "countryHouseDTO.otherPastures")
    @Mapping(target = "pastureConservation", source = "countryHouseDTO.pastureConservation")
    @Mapping(target = "hasSmoothWireFence", source = "countryHouseDTO.hasSmoothWireFence")
    @Mapping(target = "topography", source = "countryHouseDTO.topography")
    @Mapping(target = "hasRocks", source = "countryHouseDTO.hasRocks")
    @Mapping(target = "rivers", source = "countryHouseDTO.rivers")
    @Mapping(target = "dams", source = "countryHouseDTO.dams")
    @Mapping(target = "mainHouse", source = "countryHouseDTO.mainHouse")
    @Mapping(target = "staffHouse", source = "countryHouseDTO.staffHouse")
    @Mapping(target = "energy", source = "countryHouseDTO.energy")
    @Mapping(target = "hasOrchard", source = "countryHouseDTO.hasOrchard")
    @Mapping(target = "warehouse", source = "countryHouseDTO.warehouse")
    @Mapping(target = "accommodation", source = "countryHouseDTO.accommodation")
    @Mapping(target = "pens", source = "countryHouseDTO.pens")
    @Mapping(target = "hasLandingStrip", source = "countryHouseDTO.hasLandingStrip")
    @Mapping(target = "herdSupport", source = "countryHouseDTO.herdSupport")
    @Mapping(target = "distanceOfGyn", source = "countryHouseDTO.distanceOfGyn")
    @Mapping(target = "distanceOfCity", source = "countryHouseDTO.distanceOfCity")
    @Mapping(target = "distanceDirtRoad", source = "countryHouseDTO.distanceDirtRoad")
    @Mapping(target = "pastures", source = "countryHouseDTO.pastures")
    @Mapping(target = "address", source = "countryHouseDTO.address")
    @Mapping(target = "seller", source = "seller")
    @Mapping(target = "agent", source = "agent")
    @Mapping(target = "propertyCategory", constant = "COUNTRY_HOUSE")
    CountryHouse toEntity(CountryHouseDTO countryHouseDTO, Seller seller, Agent agent);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "countryHouseDTO.name")
    @Mapping(target = "seller", source = "seller")
    @Mapping(target = "agent", source = "agent")
    @Mapping(target = "address", source = "countryHouseDTO.address")
    @Mapping(target = "propertyCategory", constant = "COUNTRY_HOUSE")
    CountryHouse updateEntityFromDTO(CountryHouseDTO countryHouseDTO, @MappingTarget CountryHouse entity, Seller seller, Agent agent);

    default AreaMeasurementDTO map(Double value) {
        if (value == null) {
            return null;
        }
        return new AreaMeasurementDTO(value);
    }
}

