package com.realestate.mapper;

import com.realestate.dto.AgentDTO;
import com.realestate.entity.person.Agent;
import com.realestate.entity.property.Property;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AgentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "prospectedProperties", ignore = true)
    @Mapping(target = "password", source = "password")
    Agent toEntity(AgentDTO agentDTO);

    @Mapping(target = "prospectedProperties", expression = "java(mapToIds(agent.getProspectedProperties()))")
    @Mapping(target = "password", ignore = true)
    AgentDTO toDTO(Agent agent);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "prospectedProperties", ignore = true)
    void updateEntityFromDTO(AgentDTO agentDTO, @MappingTarget Agent agent);

    default List<Long> mapToIds(List<Property> properties) {
        return properties != null
                ? properties.stream()
                .map(Property::getId)
                .collect(Collectors.toList())
                : null;
    }
}
