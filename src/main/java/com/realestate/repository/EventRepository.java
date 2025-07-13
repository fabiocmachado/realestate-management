package com.realestate.repository;

import com.realestate.entity.event.Event;
import com.realestate.entity.person.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByAgent(Agent agent);

}
