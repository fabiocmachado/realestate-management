package com.realestate.mapper;

import com.realestate.dto.CommercialBuildingDTO;
import com.realestate.entity.property.urban.comercial.CommercialBuilding;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CommercialBuildingMapper {

    @Mapping(source = "agent.id", target = "agentId")
    @Mapping(source = "seller.id", target = "sellerId")
    CommercialBuildingDTO toDTO(CommercialBuilding commercialBuilding);

    @Mapping(source = "agentId", target = "agent.id")
    @Mapping(source = "sellerId", target = "seller.id")
    @Mapping(target = "id", ignore = true)
    CommercialBuilding toEntity(CommercialBuildingDTO commercialBuildingDTO);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(CommercialBuildingDTO commercialBuildingDTO, @MappingTarget CommercialBuilding commercialBuilding);
}
