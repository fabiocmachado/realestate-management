package com.realestate.service;

import com.realestate.dto.PropertyDTO;
import com.realestate.entity.property.Property;
import com.realestate.entity.property.urban.comercial.Commercial;
import com.realestate.entity.property.urban.comercial.CommercialArea;
import com.realestate.entity.property.urban.comercial.CommercialBuilding;
import com.realestate.entity.property.urban.comercial.CommercialRoom;
import com.realestate.entity.property.rural.Farm;
import com.realestate.entity.property.rural.CountryHouse;
import com.realestate.entity.property.rural.Rural;
import com.realestate.repository.PropertyRepository;
import com.realestate.repository.CommercialAreaRepository;
import com.realestate.repository.CommercialBuildingRepository;
import com.realestate.repository.CommercialRepository;
import com.realestate.repository.CommercialRoomRepository;
import com.realestate.repository.FarmRepository;
import com.realestate.repository.CountryHouseRepository;
import com.realestate.repository.RuralRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private CommercialRepository commercialRepository;

    @Autowired
    private CommercialAreaRepository commercialAreaRepository;

    @Autowired
    private CommercialBuildingRepository commercialBuildingRepository;

    @Autowired
    private CommercialRoomRepository commercialRoomRepository;

    @Autowired
    private RuralRepository ruralRepository;

    @Autowired
    private FarmRepository farmRepository;

    @Autowired
    private CountryHouseRepository countryHouseRepository;

    public Page<PropertyDTO> getAllProperties(Pageable pageable) {
        Page<Property> propertyPage = propertyRepository.findAll(pageable);
        return propertyPage.map(this::convertToDTO);
    }
    private PropertyDTO convertToDTO(Property property) {
        PropertyDTO propertyDTO = new PropertyDTO();
        propertyDTO.setId(property.getId());
        propertyDTO.setPropertyCode(property.getPropertyCode());
        propertyDTO.setPrice(property.getPrice());
        propertyDTO.setDescription(property.getDescription());
        propertyDTO.setStatus(property.getStatus());
        propertyDTO.setSellerId(property.getSeller().getId());
        propertyDTO.setAgentId(property.getAgent().getId());
        propertyDTO.setPropertyCategory(property.getPropertyCategory());

        return propertyDTO;
    }

    public Page<Commercial> getAllCommercialProperties(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return commercialRepository.findAll(pageable);
    }

    public Page<CommercialArea> getAllCommercialAreas(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return commercialAreaRepository.findAll(pageable);
    }

    public Page<CommercialBuilding> getAllCommercialBuildings(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return commercialBuildingRepository.findAll(pageable);
    }

    public Page<CommercialRoom> getAllCommercialRooms(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return commercialRoomRepository.findAll(pageable);
    }

    public Page<Rural> getAllRuralProperties(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ruralRepository.findAll(pageable);
    }

    public Page<Farm> getAllFarms(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return farmRepository.findAll(pageable);
    }

    public Page<CountryHouse> getAllCountryHouses(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return countryHouseRepository.findAll(pageable);
    }


}
