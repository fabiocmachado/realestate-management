package com.realestate.service;

import com.realestate.dto.EventDTO;
import com.realestate.entity.event.Event;
import com.realestate.entity.person.Agent;
import com.realestate.mapper.EventMapper;
import com.realestate.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class EventService {

    private final EventRepository repository;
    private final EventMapper mapper;

    public EventService(EventRepository repository, EventMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public EventDTO createEvent(EventDTO dto, Agent agent) {
        Event event = mapper.toEntity(dto, agent);
        Event saved = repository.save(event);
        return mapper.toDTO(saved);
    }

    public List<EventDTO> getAllEventsForAgent(Agent agent) {
        return repository.findByAgent(agent).stream()
                .map(mapper::toDTO)
                .toList();
    }

    public List<EventDTO> getAllEvents() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    public void deleteEvent(Long id) {
        repository.deleteById(id);
    }

    public Optional<EventDTO> updateEvent(Long id, EventDTO updatedDto, Agent agent) {
        return repository.findById(id).map(event -> {
            event.setTitle(updatedDto.getTitle());
            event.setDescription(updatedDto.getDescription());
            event.setStartTime(updatedDto.getStart());
            event.setEndTime(updatedDto.getEnd());
            event.setAgent(agent);
            Event updated = repository.save(event);
            return mapper.toDTO(updated);
        });
    }
}
