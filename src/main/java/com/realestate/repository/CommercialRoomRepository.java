package com.realestate.repository;

import com.realestate.entity.property.urban.comercial.CommercialRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommercialRoomRepository extends JpaRepository<CommercialRoom, Long> {

    CommercialRoom findByPropertyCode(String propertyCode);

}
