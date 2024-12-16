package com.realestate.mapper;

import com.realestate.dto.AgentDTO;
import com.realestate.entity.person.Agent;
import com.realestate.entity.property.Property;
import com.realestate.entity.property.rural.CountryHouse;
import com.realestate.entity.property.rural.Farm;
import com.realestate.entity.property.urban.comercial.CommercialArea;
import com.realestate.entity.property.urban.comercial.CommercialBuilding;
import com.realestate.entity.property.urban.comercial.CommercialRoom;
import com.realestate.entity.property.urban.residential.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AgentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "prospectedProperties", ignore = true)
    Agent toEntity(AgentDTO agentDTO);

    @Mapping(target = "prospectedProperties", source = "prospectedProperties")
    AgentDTO toDTO(Agent agent);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "prospectedProperties", ignore = true)
    void updateEntityFromDTO(AgentDTO agentDTO, @MappingTarget Agent agent);

    default List<Long> map(Set<Property> properties) {
        return properties != null ? properties.stream()
                .map(Property::getId)
                .collect(Collectors.toList())
                : List.of();
    }

    default Set<Property> map(List<Long> ids, String propertyType) {
        if (ids == null) {
            return Set.of();
        }

        return ids.stream()
                .map(id -> {
                    Property property;
                    switch (propertyType) {
                        case "UrbanLand":
                            property = new UrbanLand();
                            break;
                        case "Farm":
                            property = new Farm();
                            break;
                        case "CountryHouse":
                            property = new CountryHouse();
                            break;
                        case "CommercialArea":
                            property = new CommercialArea();
                            break;
                        case "CommercialBuilding":
                            property = new CommercialBuilding();
                            break;
                        case "CommercialRoom":
                            property = new CommercialRoom();
                            break;
                        case "House":
                            property = new House();
                            break;
                        case "Townhouse":
                            property = new Townhouse();
                            break;
                        case "Penthouse":
                            property = new Penthouse();
                            break;
                        default:
                            throw new IllegalArgumentException("Tipo de propriedade desconhecido");
                    }
                    property.setId(id);
                    return property;
                })
                .collect(Collectors.toSet());
    }
}
