package com.realestate.mapper;

import com.realestate.dto.TownhouseDTO;
import com.realestate.entity.property.urban.residential.Townhouse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TownhouseMapper {

    @Mapping(source = "agent.id", target = "agentId")
    @Mapping(source = "seller.id", target = "sellerId")
    TownhouseDTO toDTO(Townhouse townhouse);

    @Mapping(source = "agentId", target = "agent.id")
    @Mapping(source = "sellerId", target = "seller.id")
    @Mapping(target = "id", ignore = true)
    Townhouse toEntity(TownhouseDTO townhouseDTO);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(TownhouseDTO townhouseDTO, @MappingTarget Townhouse townhouse);
}