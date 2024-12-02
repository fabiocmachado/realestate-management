package com.realestate.mapper;

import com.realestate.dto.WarehouseDTO;
import com.realestate.entity.person.Agent;
import com.realestate.entity.person.Seller;
import com.realestate.entity.property.urban.comercial.Warehouse;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface WarehouseMapper {

    @Mapping(target = "propertyCode", source = "entity.propertyCode")
    @Mapping(target = "price", source = "entity.price")
    @Mapping(target = "address", source = "entity.address")
    @Mapping(target = "description", source = "entity.description")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "sellerId", source = "entity.seller.id")
    @Mapping(target = "agentId", source = "entity.agent.id")
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "orientation", source = "entity.orientation")
    @Mapping(target = "propertyCategory", constant = "WAREHOUSE")
    WarehouseDTO toDTO(Warehouse entity);

    @Mapping(target = "propertyCode", source = "dto.propertyCode")
    @Mapping(target = "price", source = "dto.price")
    @Mapping(target = "address", source = "dto.address")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "status", source = "dto.status")
    @Mapping(target = "seller", source = "seller")
    @Mapping(target = "agent", source = "agent")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orientation", source = "dto.orientation")
    @Mapping(target = "propertyCategory", constant = "WAREHOUSE")
    Warehouse toEntity(WarehouseDTO dto, Seller seller, Agent agent);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "seller", source = "seller")
    @Mapping(target = "agent", source = "agent")
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "address", source = "dto.address")
    @Mapping(target = "orientation", source = "dto.orientation")
    @Mapping(target = "propertyCategory", constant = "WAREHOUSE")
    Warehouse updateEntityFromDTO(@MappingTarget Warehouse entity, WarehouseDTO dto, Seller seller, Agent agent);
}
