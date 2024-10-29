package com.realestate.controller;

import com.realestate.dto.AgentDTO;
import com.realestate.service.AgentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("agents")
@RequiredArgsConstructor
public class AgentController {

    private final AgentService agentService;

    @GetMapping
    public ResponseEntity<List<AgentDTO>> listAllAgents() {
        return ResponseEntity.ok(agentService.findAllAgents());
    }

    @PostMapping
    public ResponseEntity<AgentDTO> createAgent(@Valid @RequestBody AgentDTO agentDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(agentService.createAgent(agentDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgentDTO> getAgentById(@PathVariable Long id) {
        return agentService.findAgentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgentDTO> updateAgent(
            @PathVariable Long id,
            @Valid @RequestBody AgentDTO agentDTO) {
        return ResponseEntity.ok(agentService.updateAgent(id, agentDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgent(@PathVariable Long id) {
        agentService.deleteAgent(id);
        return ResponseEntity.noContent().build();
    }
}