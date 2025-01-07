package com.realestate.mapper;

import com.realestate.dto.UrbanLandDTO;
import com.realestate.entity.person.Agent;
import com.realestate.entity.person.Seller;
import com.realestate.entity.property.urban.residential.UrbanLand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UrbanLandMapper {

    @Mapping(source = "agent.id", target = "agentId")
    @Mapping(source = "seller.id", target = "sellerId")
    UrbanLandDTO toDTO(UrbanLand urbanLand);

    @Mapping(source = "agentId", target = "agent.id")
    @Mapping(source = "sellerId", target = "seller.id")
    @Mapping(target = "id", ignore = true)
    UrbanLand toEntity(UrbanLandDTO urbanLandDTO);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDTO(UrbanLandDTO urbanLandDTO, @MappingTarget UrbanLand urbanLand);

    default Agent mapAgent(Long agentId) {
        if (agentId == null) {
            return null;
        }
        Agent agent = new Agent();
        agent.setId(agentId);
        return agent;
    }

    default Seller mapSeller(Long sellerId) {
        if (sellerId == null) {
            return null;
        }
        Seller seller = new Seller();
        seller.setId(sellerId);
        return seller;
    }
}

