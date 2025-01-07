package com.realestate.mapper;

import com.realestate.dto.FarmDTO;
import com.realestate.entity.property.rural.Farm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface FarmMapper {

    @Mapping(source = "agent.id", target = "agentId")
    @Mapping(source = "seller.id", target = "sellerId")
    FarmDTO toDTO(Farm farm);

    @Mapping(source = "agentId", target = "agent.id")
    @Mapping(source = "sellerId", target = "seller.id")
    @Mapping(target = "id", ignore = true)
    Farm toEntity(FarmDTO farmDTO);

    @Mapping(target = "id", ignore = true)
    Farm updateEntityFromDTO(FarmDTO farmDTO, @MappingTarget Farm farm);
}

