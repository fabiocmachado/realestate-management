package com.realestate.mapper;

import com.realestate.dto.UrbanLandDTO;
import com.realestate.entity.person.Agent;
import com.realestate.entity.person.Seller;
import com.realestate.entity.property.urban.residential.UrbanLand;
import org.mapstruct.*;
import org.springframework.boot.context.properties.PropertyMapper;

@Mapper(componentModel = "spring", uses = {PropertyMapper.class}) // Use outros mappers se necessário
public interface UrbanLandMapper {

    @Mapping(target = "propertyCode", source = "entity.propertyCode")
    @Mapping(target = "price", source = "entity.price")
    @Mapping(target = "address", source = "entity.address")
    @Mapping(target = "description", source = "entity.description")
    @Mapping(target = "status", source = "entity.status")
    @Mapping(target = "sellerId", source = "entity.seller.id")
    @Mapping(target = "id", source = "entity.id")
    UrbanLandDTO toDTO(UrbanLand entity);

    @Mapping(target = "propertyCode", source = "dto.propertyCode")
    @Mapping(target = "price", source = "dto.price")
    @Mapping(target = "address", source = "dto.address")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "status", source = "dto.status")
    @Mapping(target = "seller", source = "seller") //
    @Mapping(target = "agent", source = "agent")
    @Mapping(target = "id", ignore = true)
    UrbanLand toEntity(UrbanLandDTO dto, Seller seller, Agent agent);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "propertyCode", source = "dto.propertyCode")
    @Mapping(target = "price", source = "dto.price")
    @Mapping(target = "address", source = "dto.address")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "status", source = "dto.status")
    @Mapping(target = "seller", source = "seller")
    @Mapping(target = "agent", source = "agent")
    @Mapping(target = "id", source = "dto.id")
    void updateEntity(@MappingTarget UrbanLand entity, UrbanLandDTO dto, Seller seller, Agent agent);
}

