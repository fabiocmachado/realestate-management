package com.realestate.mapper;

import com.realestate.dto.EventDTO;
import com.realestate.entity.event.Event;
import com.realestate.entity.person.Agent;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {

    public EventDTO toDTO(Event event) {
        if (event == null) {
            return null;
        }

        EventDTO dto = new EventDTO();
        dto.setId(event.getId());
        dto.setTitle(event.getTitle());
        dto.setDescription(event.getDescription());
        dto.setStart(event.getStartTime());
        dto.setEnd(event.getEndTime());
        if (event.getAgent() != null) {
            dto.setAgentId(event.getAgent().getId());
        }
        return dto;
    }

    public Event toEntity(EventDTO dto, Agent agent) {
        if (dto == null) {
            return null;
        }

        Event event = new Event();
        event.setId(dto.getId());
        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setStartTime(dto.getStart());
        event.setEndTime(dto.getEnd());
        event.setAgent(agent);
        return event;
    }
}
