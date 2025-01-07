package com.realestate.mapper;

import com.realestate.dto.CommercialAreaDTO;
import com.realestate.entity.property.urban.comercial.CommercialArea;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CommercialAreaMapper {

    @Mapping(source = "agent.id", target = "agentId")
    @Mapping(source = "seller.id", target = "sellerId")
    CommercialAreaDTO toDTO(CommercialArea commercialArea);

    @Mapping(source = "agentId", target = "agent.id")
    @Mapping(source = "sellerId", target = "seller.id")
    @Mapping(target = "id", ignore = true)
    CommercialArea toEntity(CommercialAreaDTO commercialAreaDTO);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(CommercialAreaDTO commercialAreaDTO, @MappingTarget CommercialArea commercialArea);
}
