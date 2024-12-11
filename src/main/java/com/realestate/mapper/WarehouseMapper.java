package com.realestate.mapper;

import com.realestate.dto.WarehouseDTO;
import com.realestate.entity.property.urban.comercial.Warehouse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface WarehouseMapper {

    @Mapping(source = "agent.id", target = "agentId")
    @Mapping(source = "seller.id", target = "sellerId")
    WarehouseDTO toDTO(Warehouse warehouse);

    @Mapping(source = "agentId", target = "agent.id")
    @Mapping(source = "sellerId", target = "seller.id")
    @Mapping(target = "id", ignore = true)
    Warehouse toEntity(WarehouseDTO warehouseDTO);

    @Mapping(target = "agent", ignore = true)
    @Mapping(target = "seller", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(WarehouseDTO warehouseDTO, @MappingTarget Warehouse warehouse);
}

