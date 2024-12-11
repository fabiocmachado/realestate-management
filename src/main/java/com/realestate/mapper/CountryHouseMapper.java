package com.realestate.mapper;

import com.realestate.dto.CountryHouseDTO;
import com.realestate.entity.property.rural.CountryHouse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CountryHouseMapper {

    @Mapping(source = "agent.id", target = "agentId")
    @Mapping(source = "seller.id", target = "sellerId")
    CountryHouseDTO toDTO(CountryHouse countryHouse);

    @Mapping(source = "agentId", target = "agent.id")
    @Mapping(source = "sellerId", target = "seller.id")
    @Mapping(target = "id", ignore = true)
    CountryHouse toEntity(CountryHouseDTO countryHouseDTO);

    @Mapping(target = "agent", ignore = true)
    @Mapping(target = "seller", ignore = true)
    @Mapping(target = "id", ignore = true)
    CountryHouse updateEntityFromDTO(CountryHouseDTO countryHouseDTO, @MappingTarget CountryHouse countryHouse);
}

