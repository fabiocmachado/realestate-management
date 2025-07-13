package com.realestate.controller;

import com.realestate.dto.EventDTO;
import com.realestate.entity.person.Agent;
import com.realestate.security.CustomUserDetails;
import com.realestate.service.AgentService;
import com.realestate.service.EventService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;
    private final AgentService agentService;

    public EventController(EventService eventService, AgentService agentService) {
        this.eventService = eventService;
        this.agentService = agentService;
    }

    @GetMapping("/mine")
    public ResponseEntity<List<EventDTO>> getMyEvents() {
        Agent agent = getAuthenticatedAgent();
        return ResponseEntity.ok(eventService.getAllEventsForAgent(agent));
    }

    @PostMapping
    public ResponseEntity<EventDTO> createEvent(@RequestBody EventDTO dto) {
        Agent agent = getAuthenticatedAgent();
        EventDTO saved = eventService.createEvent(dto, agent);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Long id) {
        Agent agent = getAuthenticatedAgent();
        return eventService.getEventByIdAndAgent(id, agent)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable Long id, @RequestBody EventDTO dto) {
        Agent agent = getAuthenticatedAgent();
        return eventService.updateEvent(id, dto, agent)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    private Agent getAuthenticatedAgent() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CustomUserDetails userDetails) {
            Long agentId = userDetails.getAgentId();
            return agentService.findAgentEntityById(agentId)
                    .orElseThrow(() -> new EntityNotFoundException("Agente não encontrado com id: " + agentId));
        }
        throw new IllegalStateException("Usuário não autenticado");
    }


}
