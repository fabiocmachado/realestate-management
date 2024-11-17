package com.realestate.mapper;

import com.realestate.dto.TownhouseDTO;
import com.realestate.entity.person.Agent;
import com.realestate.entity.person.Seller;
import com.realestate.entity.property.urban.residential.Townhouse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {HouseMapper.class})
public interface TownhouseMapper {

    @Mapping(target = "propertyCode", source = "entity.propertyCode")
    @Mapping(target = "price", source = "entity.price")
    @Mapping(target = "address", source = "entity.address")
    @Mapping(target = "description", source = "entity.description")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "sellerId", source = "entity.seller.id")
    @Mapping(target = "agentId", source = "entity.agent.id")
    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "bedrooms", source = "entity.bedrooms")
    @Mapping(target = "bathrooms", source = "entity.bathrooms")
    @Mapping(target = "garageSpaces", source = "entity.garageSpaces")
    TownhouseDTO toDto(Townhouse entity);

    @Mapping(target = "propertyCode", source = "dto.propertyCode")
    @Mapping(target = "price", source = "dto.price")
    @Mapping(target = "address", source = "dto.address")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "status", source = "dto.status")
    @Mapping(target = "seller", source = "seller") // seller passará como parâmetro
    @Mapping(target = "agent", source = "agent")   // agent passará como parâmetro
    @Mapping(target = "id", ignore = true)          // Ignore o ID para criação
    @Mapping(target = "bedrooms", source = "dto.bedrooms")
    @Mapping(target = "bathrooms", source = "dto.bathrooms")
    @Mapping(target = "garageSpaces", source = "dto.garageSpaces")
    Townhouse toEntity(TownhouseDTO dto, Seller seller, Agent agent);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "propertyCode", source = "dto.propertyCode")
    @Mapping(target = "price", source = "dto.price")
    @Mapping(target = "address", source = "dto.address")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "status", source = "dto.status")
    @Mapping(target = "seller", source = "seller")
    @Mapping(target = "agent", source = "agent")
    @Mapping(target = "id", source = "dto.id")
    @Mapping(target = "bedrooms", source = "dto.bedrooms")
    @Mapping(target = "bathrooms", source = "dto.bathrooms")
    @Mapping(target = "garageSpaces", source = "dto.garageSpaces")
    void updateEntity(@MappingTarget Townhouse entity, TownhouseDTO dto, Seller seller, Agent agent);
}
