package com.realestate.mapper;

import com.realestate.dto.HouseDTO;
import com.realestate.entity.property.urban.residential.House;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface HouseMapper {

    @Mapping(source = "agent.id", target = "agentId")
    @Mapping(source = "seller.id", target = "sellerId")
    HouseDTO toDTO(House house);

    @Mapping(source = "agentId", target = "agent.id")
    @Mapping(source = "sellerId", target = "seller.id")
    @Mapping(target = "id", ignore = true)
    House toEntity(HouseDTO houseDTO);

    @Mapping(target = "agent", ignore = true)
    @Mapping(target = "seller", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(HouseDTO houseDTO, @MappingTarget House house);
}
