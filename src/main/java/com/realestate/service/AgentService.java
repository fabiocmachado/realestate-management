package com.realestate.service;

import com.realestate.dto.AgentDTO;
import com.realestate.entity.person.Agent;
import com.realestate.exception.BusinessException;
import com.realestate.repository.AgentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AgentService {

    private final AgentRepository agentRepository;

    @Transactional(readOnly = true)
    public List<AgentDTO> findAllAgents() {
        return agentRepository.findAll().stream()
                .map(AgentDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<AgentDTO> findAgentById(Long id) {
        return agentRepository.findByIdWithProperties(id)
                .map(AgentDTO::fromEntity);
    }

    @Transactional
    public AgentDTO createAgent(AgentDTO agentDTO) {
        Agent agent = agentDTO.toEntity();
        validateNewAgent(agent);
        return AgentDTO.fromEntity(agentRepository.save(agent));
    }

    @Transactional
    public AgentDTO updateAgent(Long id, AgentDTO agentDTO) {
        return agentRepository.findById(id)
                .map(existingAgent -> updateExistingAgent(existingAgent, agentDTO))
                .map(AgentDTO::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Agente não encontrado com ID: " + id));
    }

    @Transactional
    public void deleteAgent(Long id) {
        Agent agent = agentRepository.findByIdWithProperties(id)
                .orElseThrow(() -> new EntityNotFoundException("Agente não encontrado com ID: " + id));

        if (!agent.getProspectedProperties().isEmpty()) {
            throw new BusinessException("Não é possível excluir um agente com propriedades vinculadas");
        }

        agentRepository.deleteById(id);
    }

    private void validateNewAgent(Agent agent) {
        if (agentRepository.existsByCpf(agent.getCpf())) {
            throw new BusinessException("CPF já cadastrado");
        }
        if (agentRepository.existsByLicenseNumber(agent.getLicenseNumber())) {
            throw new BusinessException("Número de licença já cadastrado");
        }
    }

    private Agent updateExistingAgent(Agent existingAgent, AgentDTO agentDTO) {
        if (!existingAgent.getCpf().equals(agentDTO.getCpf()) &&
                agentRepository.existsByCpf(agentDTO.getCpf())) {
            throw new BusinessException("CPF já cadastrado para outro agente");
        }

        if (!existingAgent.getLicenseNumber().equals(agentDTO.getLicenseNumber()) &&
                agentRepository.existsByLicenseNumber(agentDTO.getLicenseNumber())) {
            throw new BusinessException("Número de licença já cadastrado para outro agente");
        }

        Agent newAgent = agentDTO.toEntity();
        existingAgent.setName(newAgent.getName());
        existingAgent.setEmail(newAgent.getEmail());
        existingAgent.setPhone(newAgent.getPhone());
        existingAgent.setLicenseNumber(newAgent.getLicenseNumber());
        existingAgent.setAddress(newAgent.getAddress());
        existingAgent.setRg(newAgent.getRg());
        existingAgent.setHiringDate(newAgent.getHiringDate());

        return agentRepository.save(existingAgent);
    }
}