package com.realestate.mapper;

import com.realestate.dto.CommercialAreaDTO;
import com.realestate.entity.person.Agent;
import com.realestate.entity.person.Seller;
import com.realestate.entity.property.urban.comercial.CommercialArea;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CommercialAreaMapper {

    @Mapping(target = "propertyCode", source = "dto.propertyCode")
    @Mapping(target = "price", source = "dto.price")
    @Mapping(target = "address", source = "dto.address")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "status", source = "dto.status")
    @Mapping(target = "seller", source = "seller")
    @Mapping(target = "agent", source = "agent")
    @Mapping(target = "id", ignore = true)
    CommercialArea toEntity(CommercialAreaDTO dto, Seller seller, Agent agent);

    @Mapping(target = "propertyCode", source = "entity.propertyCode")
    @Mapping(target = "price", source = "entity.price")
    @Mapping(target = "address", source = "entity.address")
    @Mapping(target = "description", source = "entity.description")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "sellerId", source = "entity.seller.id")
    @Mapping(target = "agentId", source = "entity.agent.id")
    @Mapping(target = "id", source = "entity.id")
    CommercialAreaDTO toDTO(CommercialArea entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "propertyCode", source = "dto.propertyCode")
    @Mapping(target = "price", source = "dto.price")
    @Mapping(target = "address", source = "dto.address")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "status", source = "dto.status")
    @Mapping(target = "seller", source = "seller")
    @Mapping(target = "agent", source = "agent")
    @Mapping(target = "id", source = "dto.id")
    CommercialArea updateEntityFromDTO(@MappingTarget CommercialArea entity, CommercialAreaDTO dto, Seller seller, Agent agent);
}
