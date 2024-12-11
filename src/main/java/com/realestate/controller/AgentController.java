package com.realestate.controller;

import com.realestate.dto.AgentDTO;
import com.realestate.service.AgentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

@RestController
@RequestMapping("/agents")
@RequiredArgsConstructor
public class AgentController {

    private final AgentService agentService;

    @GetMapping
    public ResponseEntity<List<AgentDTO>> listAllAgents() {
        List<AgentDTO> agents = agentService.findAllAgents();
        return ResponseEntity.ok(agents);
    }

    @PostMapping
    public ResponseEntity<Object> createAgent(@Valid @RequestBody AgentDTO agentDTO, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            for (ObjectError error : result.getAllErrors()) {
                errors.append(error.getDefaultMessage()).append(". ");
            }
            return ResponseEntity.badRequest().body(errors.toString());
        }

        AgentDTO createdAgent = agentService.registerAgent(agentDTO);
        return new ResponseEntity<>(createdAgent, HttpStatus.CREATED);
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
            @Valid @RequestBody AgentDTO agentDTO,
            BindingResult result) {

        if (result.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            for (ObjectError error : result.getAllErrors()) {
                errors.append(error.getDefaultMessage()).append(". ");
            }
            return ResponseEntity.badRequest().body(null);
        }

        return agentService.findAgentById(id)
                .map(existingAgent -> {
                    AgentDTO updatedAgent = agentService.updateAgent(id, agentDTO);
                    return ResponseEntity.ok(updatedAgent);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgent(@PathVariable Long id) {
        if (agentService.findAgentById(id).isPresent()) {
            agentService.deleteAgent(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
