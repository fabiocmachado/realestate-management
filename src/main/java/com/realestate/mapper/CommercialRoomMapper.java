package com.realestate.mapper;

import com.realestate.dto.CommercialRoomDTO;
import com.realestate.entity.property.urban.comercial.CommercialRoom;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CommercialRoomMapper {

    @Mapping(source = "agent.id", target = "agentId")
    @Mapping(source = "seller.id", target = "sellerId")
    CommercialRoomDTO toDTO(CommercialRoom commercialRoom);

    @Mapping(source = "agentId", target = "agent.id")
    @Mapping(source = "sellerId", target = "seller.id")
    @Mapping(target = "id", ignore = true)
    CommercialRoom toEntity(CommercialRoomDTO commercialRoomDTO);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(CommercialRoomDTO commercialRoomDTO, @MappingTarget CommercialRoom commercialRoom);
}
