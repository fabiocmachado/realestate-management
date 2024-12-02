package com.realestate.mapper;

import com.realestate.dto.AreaMeasurementDTO;
import com.realestate.dto.FarmDTO;
import com.realestate.entity.person.Agent;
import com.realestate.entity.person.Seller;
import com.realestate.entity.property.rural.Farm;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface FarmMapper {

    @Mapping(target = "name", source = "entity.name")
    @Mapping(target = "city", source = "entity.city")
    @Mapping(target = "state", source = "entity.state")
    @Mapping(target = "formedArea", source = "entity.formedArea.alqueiresGoianos")
    @Mapping(target = "typeOfSoil", source = "entity.typeOfSoil")
    @Mapping(target = "predominantPasture", source = "entity.predominantPasture")
    @Mapping(target = "otherPastures", source = "entity.otherPastures")
    @Mapping(target = "pastureConservation", source = "entity.pastureConservation")
    @Mapping(target = "smoothWireFence", source = "entity.smoothWireFence")
    @Mapping(target = "topography", source = "entity.topography")
    @Mapping(target = "rocks", source = "entity.rocks")
    @Mapping(target = "rivers", source = "entity.rivers")
    @Mapping(target = "dams", source = "entity.dams")
    @Mapping(target = "mainHouse", source = "entity.mainHouse")
    @Mapping(target = "staffHouse", source = "entity.staffHouse")
    @Mapping(target = "energy", source = "entity.energy")
    @Mapping(target = "orchard", source = "entity.orchard")
    @Mapping(target = "warehouse", source = "entity.warehouse")
    @Mapping(target = "accommodation", source = "entity.accommodation")
    @Mapping(target = "pens", source = "entity.pens")
    @Mapping(target = "landingStrip", source = "entity.landingStrip")
    @Mapping(target = "herdSupport", source = "entity.herdSupport")
    @Mapping(target = "distanceOfGyn", source = "entity.distanceOfGyn")
    @Mapping(target = "distanceOfCity", source = "entity.distanceOfCity")
    @Mapping(target = "distanceDirtRoad", source = "entity.distanceDirtRoad")
    @Mapping(target = "pastures", source = "entity.pastures")
    @Mapping(target = "address", source = "entity.address")
    @Mapping(target = "seller", source = "entity.seller")
    @Mapping(target = "agent", source = "entity.agent")
    @Mapping(target = "propertyCategory", constant = "FARM")
    FarmDTO toDTO(Farm entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "farmDTO.name")
    @Mapping(target = "city", source = "farmDTO.city")
    @Mapping(target = "state", source = "farmDTO.state")
    @Mapping(target = "formedArea", source = "farmDTO.formedArea")
    @Mapping(target = "typeOfSoil", source = "farmDTO.typeOfSoil")
    @Mapping(target = "predominantPasture", source = "farmDTO.predominantPasture")
    @Mapping(target = "otherPastures", source = "farmDTO.otherPastures")
    @Mapping(target = "pastureConservation", source = "farmDTO.pastureConservation")
    @Mapping(target = "smoothWireFence", source = "farmDTO.smoothWireFence")
    @Mapping(target = "topography", source = "farmDTO.topography")
    @Mapping(target = "rocks", source = "farmDTO.rocks")
    @Mapping(target = "rivers", source = "farmDTO.rivers")
    @Mapping(target = "dams", source = "farmDTO.dams")
    @Mapping(target = "mainHouse", source = "farmDTO.mainHouse")
    @Mapping(target = "staffHouse", source = "farmDTO.staffHouse")
    @Mapping(target = "energy", source = "farmDTO.energy")
    @Mapping(target = "orchard", source = "farmDTO.orchard")
    @Mapping(target = "warehouse", source = "farmDTO.warehouse")
    @Mapping(target = "accommodation", source = "farmDTO.accommodation")
    @Mapping(target = "pens", source = "farmDTO.pens")
    @Mapping(target = "landingStrip", source = "farmDTO.landingStrip")
    @Mapping(target = "herdSupport", source = "farmDTO.herdSupport")
    @Mapping(target = "distanceOfGyn", source = "farmDTO.distanceOfGyn")
    @Mapping(target = "distanceOfCity", source = "farmDTO.distanceOfCity")
    @Mapping(target = "distanceDirtRoad", source = "farmDTO.distanceDirtRoad")
    @Mapping(target = "pastures", source = "farmDTO.pastures")
    @Mapping(target = "address", source = "farmDTO.address")
    @Mapping(target = "seller", source = "seller")
    @Mapping(target = "agent", source = "agent")
    @Mapping(target = "propertyCategory", constant = "FARM")
    Farm toEntity(FarmDTO farmDTO, Seller seller, Agent agent);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "farmDTO.name")
    @Mapping(target = "city", source = "farmDTO.city")
    @Mapping(target = "state", source = "farmDTO.state")
    @Mapping(target = "formedArea", source = "farmDTO.formedArea")
    @Mapping(target = "seller", source = "seller")
    @Mapping(target = "agent", source = "agent")
    @Mapping(target = "address", source = "farmDTO.address")
    @Mapping(target = "propertyCategory", constant = "FARM")
    Farm updateEntityFromDTO(FarmDTO farmDTO, @MappingTarget Farm entity, Seller seller, Agent agent);


    default AreaMeasurementDTO map(Double value) {
        return value == null ? null : new AreaMeasurementDTO(value);
    }
}
