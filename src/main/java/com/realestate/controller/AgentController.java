package com.realestate.controller;

import com.realestate.dto.AgentDTO;
import com.realestate.entity.person.Agent;
import com.realestate.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/agent")
public class AgentController {

    @Autowired
    private AgentService agentService;

    @GetMapping()
    public ResponseEntity<List<AgentDTO>> listAllAgents() {
        List<Agent> agents = agentService.findAllAgents();
        List<AgentDTO> agentDTOs = agents.stream()
                .map(agentService::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(agentDTOs);
    }

    @PostMapping()
    public ResponseEntity<AgentDTO> createAgent(@RequestBody AgentDTO agentDTO) {
        Agent agent = agentService.convertToEntity(agentDTO);
        agentService.createAgent(agent);
        return ResponseEntity.status(HttpStatus.CREATED).body(agentService.convertToDTO(agent));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgentDTO> getAgentById(@PathVariable Long id) {
        Optional<Agent> agent = agentService.findAgentById(id);
        return agent.map(value -> ResponseEntity.ok(agentService.convertToDTO(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgentDTO> updateAgent(@PathVariable Long id, @RequestBody AgentDTO agentDTO) {
        Agent agent = agentService.updateAgent(id, agentService.convertToEntity(agentDTO));
        return ResponseEntity.ok(agentService.convertToDTO(agent));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgent(@PathVariable Long id) {
        agentService.deleteAgent(id);
        return ResponseEntity.noContent().build();
    }
}


