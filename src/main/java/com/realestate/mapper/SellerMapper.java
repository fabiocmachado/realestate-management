package com.realestate.mapper;

import com.realestate.dto.SellerDTO;
import com.realestate.entity.person.Seller;
import com.realestate.entity.property.Property;
import com.realestate.repository.PropertyRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface SellerMapper {

    @Mapping(target = "properties", expression = "java(mapPropertiesToIds(seller.getProperties()))")
    SellerDTO toDTO(Seller seller);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "properties", expression = "java(mapIdsToProperties(sellerDTO.getProperties(), propertyRepository))")
    Seller toEntity(SellerDTO sellerDTO, PropertyRepository propertyRepository);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "properties", expression = "java(mapIdsToProperties(sellerDTO.getProperties(), propertyRepository))")
    void updateEntityFromDTO(SellerDTO sellerDTO, @MappingTarget Seller seller, PropertyRepository propertyRepository);

    default List<Long> mapPropertiesToIds(List<Property> properties) {
        return properties != null
                ? properties.stream().map(Property::getId).collect(Collectors.toList())
                : null;
    }

    default List<Property> mapIdsToProperties(List<Long> ids, PropertyRepository repository) {
        return ids != null && !ids.isEmpty()
                ? repository.findByIdIn(ids)
                : null;
    }

    List<SellerDTO> toDTOList(List<Seller> sellers);
}
