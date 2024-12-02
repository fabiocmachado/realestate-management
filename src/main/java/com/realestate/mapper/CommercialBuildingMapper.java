package com.realestate.mapper;

import com.realestate.dto.CommercialBuildingDTO;
import com.realestate.entity.person.Agent;
import com.realestate.entity.person.Seller;
import com.realestate.entity.property.urban.comercial.CommercialBuilding;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CommercialBuildingMapper {

    @Mapping(target = "sellerId", source = "entity.seller.id")
    @Mapping(target = "agentId", source = "entity.prospectedBy.id")
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "address", source = "entity.address")
    @Mapping(target = "orientation", source = "entity.orientation")
    @Mapping(target = "propertyCategory", constant = "COMMERCIAL_BUILDING")
    CommercialBuildingDTO toDTO(CommercialBuilding entity);

    @Mapping(target = "seller", source = "seller")
    @Mapping(target = "prospectedBy", source = "agent")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "address", source = "dto.address")
    @Mapping(target = "orientation", source = "dto.orientation")
    @Mapping(target = "propertyCategory", constant = "COMMERCIAL_BUILDING")
    CommercialBuilding toEntity(CommercialBuildingDTO dto, Seller seller, Agent agent);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "seller", source = "seller")
    @Mapping(target = "prospectedBy", source = "agent")
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "address", source = "dto.address")
    @Mapping(target = "orientation", source = "dto.orientation")
    @Mapping(target = "propertyCategory", constant = "COMMERCIAL_BUILDING")
    CommercialBuilding updateEntityFromDTO(@MappingTarget CommercialBuilding entity, CommercialBuildingDTO dto, Seller seller, Agent agent);
}
