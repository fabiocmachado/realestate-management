package com.realestate.mapper;

import com.realestate.dto.ApartmentDTO;
import com.realestate.entity.property.urban.residential.Apartment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ApartmentMapper {

    @Mapping(source = "agent.id", target = "agentId")
    @Mapping(source = "seller.id", target = "sellerId")
    ApartmentDTO toDTO(Apartment apartment);

    @Mapping(source = "agentId", target = "agent.id")
    @Mapping(source = "sellerId", target = "seller.id")
    Apartment toEntity(ApartmentDTO apartmentDTO);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(ApartmentDTO apartmentDTO, @MappingTarget Apartment apartment);
}