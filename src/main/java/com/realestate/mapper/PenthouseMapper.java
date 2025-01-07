package com.realestate.mapper;

import com.realestate.dto.PenthouseDTO;
import com.realestate.entity.property.urban.residential.Penthouse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PenthouseMapper {

    @Mapping(source = "agent.id", target = "agentId")
    @Mapping(source = "seller.id", target = "sellerId")
    PenthouseDTO toDTO(Penthouse penthouse);

    @Mapping(source = "agentId", target = "agent.id")
    @Mapping(source = "sellerId", target = "seller.id")
    @Mapping(target = "id", ignore = true)
    Penthouse toEntity(PenthouseDTO penthouseDTO);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(PenthouseDTO penthouseDTO, @MappingTarget Penthouse penthouse);
}