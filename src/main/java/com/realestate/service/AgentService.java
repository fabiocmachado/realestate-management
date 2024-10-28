package com.realestate.service;

import com.realestate.entity.person.Agent;
import com.realestate.repository.AgentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgentService {

    @Autowired
    public AgentRepository agentRepository;

    public List<Agent> listAllAgents() {
        return agentRepository.findAll();
    }

    public void createAgent(Agent agent) {
        if (agent == null) {
            throw new IllegalArgumentException("Agent cannot be null");
        }
        agentRepository.save(agent);
    }

    public Optional<Agent> getAgentById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Agent ID cannot be null");
        } return agentRepository.findById(id);
    }

    public Agent updateAgent(Long id, Agent updatedAgent) {
        if (id == null || updatedAgent == null) {
            throw new IllegalArgumentException("Agent ID and updated agent cannot be null.");
        } return agentRepository.findById(id).map(agent -> {
            agent.setName(updatedAgent.getName());
            agent.setEmail(updatedAgent.getEmail());
            agent.setPhone(updatedAgent.getPhone());
            agent.setLicenseNumber(updatedAgent.getLicenseNumber());
            agent.setAddress(updatedAgent.getAddress());
            agent.setCpf(updatedAgent.getCpf());
            agent.setRg(updatedAgent.getRg());
            return agentRepository.save(agent);
        }).orElseThrow(() -> new EntityNotFoundException("Agent not found"));
    }

    public void deleteAgent(Long id) {
        Optional<Agent> agent = agentRepository.findById(id);
        if (agent.isPresent()) {
            agentRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Agent number " + id + " not found.");
        }
    }


}

