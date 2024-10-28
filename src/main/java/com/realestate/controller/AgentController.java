package com.realestate.controller;

import com.realestate.entity.person.Agent;
import com.realestate.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/agent")
public class AgentController {

    @Autowired
    private AgentService agentService;

    @GetMapping()
    public ResponseEntity<List<Agent>> listAllAgents (Agent agent){
        List<Agent> agents = agentService.listAllAgents();
        return ResponseEntity.ok(agents);
    }

    @PostMapping()
    public ResponseEntity<Agent> createAgent(@RequestBody Agent agent){
        agentService.createAgent(agent);
        return ResponseEntity.status(HttpStatus.CREATED).body(agent);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Agent> getAgentById (@PathVariable Long id){
        Optional<Agent> agent = agentService.getAgentById(id);
        return agent.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Agent> updateAgent(@PathVariable  Long id, @RequestBody Agent agentUpdated) {
        Agent agent = agentService.updateAgent(id, agentUpdated);

        if (agent == null) {
            ResponseEntity.notFound().build();
        } return ResponseEntity.ok(agent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Agent> deleteAgent(@PathVariable Long id) {
        if (id == null) {
            ResponseEntity.notFound().build();
        } agentService.deleteAgent(id);
            return ResponseEntity.noContent().build();
    }
}
