package com.realestate.service;

import com.realestate.dto.AgentDTO;
import com.realestate.entity.person.Agent;
import com.realestate.exception.AgentNotFoundException;
import com.realestate.repository.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgentService {

    @Autowired
    private AgentRepository agentRepository;

    public List<Agent> findAllAgents() {
        return agentRepository.findAll();
    }

    public void createAgent(Agent agent) {
        if (agent == null) {
            throw new IllegalArgumentException("Agent cannot be null");
        }
        agentRepository.save(agent);
    }

    public Optional<Agent> findAgentById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Agent ID cannot be null");
        }
        return agentRepository.findById(id);
    }

    public Agent updateAgent(Long id, Agent updatedAgent) {
        if (id == null || updatedAgent == null) {
            throw new IllegalArgumentException("Agent ID and updated agent cannot be null.");
        }
        return agentRepository.findById(id)
                .map(agent -> {
                    agent.setName(updatedAgent.getName());
                    agent.setEmail(updatedAgent.getEmail());
                    agent.setPhone(updatedAgent.getPhone());
                    agent.setLicenseNumber(updatedAgent.getLicenseNumber());
                    agent.setAddress(updatedAgent.getAddress());
                    agent.setCpf(updatedAgent.getCpf());
                    agent.setRg(updatedAgent.getRg());
                    return agentRepository.save(agent);
                })
                .orElseThrow(() -> new AgentNotFoundException("Agent not found"));
    }

    public void deleteAgent(Long id) {
        Optional<Agent> agent = agentRepository.findById(id);
        if (agent.isPresent()) {
            agentRepository.deleteById(id);
        } else {
            throw new AgentNotFoundException("Agent with ID " + id + " not found.");
        }
    }

    public AgentDTO convertToDTO(Agent agent) {
        return new AgentDTO(
                agent.getId(),
                agent.getName(),
                agent.getEmail(),
                agent.getPhone(),
                agent.getLicenseNumber(),
                agent.getAddress(),
                agent.getCpf(),
                agent.getRg()
        );
    }

    public Agent convertToEntity(AgentDTO agentDTO) {
        return new Agent(
                agentDTO.getId(),
                agentDTO.getName(),
                agentDTO.getCpf(),
                agentDTO.getRg(),
                agentDTO.getEmail(),
                agentDTO.getPhone(),
                agentDTO.getAddress(),
                agentDTO.getLicenseNumber(),
                null,
                null
        );
    }
}


