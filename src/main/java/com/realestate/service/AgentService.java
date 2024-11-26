package com.realestate.service;

import com.realestate.dto.AgentDTO;
import com.realestate.entity.person.Agent;
import com.realestate.entity.property.Property;
import com.realestate.exception.BusinessException;
import com.realestate.repository.AgentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;  // Adicione a importação
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AgentService {

    private final AgentRepository agentRepository;
    private final PasswordEncoder passwordEncoder; // Injetando o PasswordEncoder

    @Transactional
    public AgentDTO createFirstAdmin(AgentDTO agentDTO) {
        if (agentRepository.existsByHasAdminPermissionsTrue()) {
            throw new BusinessException("Já existe um administrador.");
        }
        agentDTO.setHasAdminPermissions(true);

        return save(agentDTO);
    }

    @Transactional(readOnly = true)
    public List<AgentDTO> findAllAgents() {
        return agentRepository.findAllByOrderByIdAsc().stream()
                .map(AgentDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<AgentDTO> findAgentById(Long id) {
        return agentRepository.findById(id)
                .map(AgentDTO::fromEntity);
    }

    @Transactional
    private AgentDTO save(AgentDTO agentDTO) {
        Agent agent = agentDTO.toEntity();
        validateAgent(agent);

        // Codificando a senha antes de salvar
        String encodedPassword = passwordEncoder.encode(agentDTO.getPassword());
        agent.setPassword(encodedPassword);

        Agent savedAgent = agentRepository.save(agent);
        return AgentDTO.fromEntity(savedAgent);
    }

    @Transactional
    public AgentDTO updateAgent(Long id, AgentDTO agentDTO) {
        Agent agent = agentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agente não encontrado com ID: " + id));

        updateExistingAgent(agent, agentDTO);

        // Codificando a senha antes de salvar ao atualizar
        if (agentDTO.getPassword() != null && !agentDTO.getPassword().isEmpty()) {
            agent.setPassword(passwordEncoder.encode(agentDTO.getPassword()));
        }

        Agent updatedAgent = agentRepository.save(agent);
        return AgentDTO.fromEntity(updatedAgent);
    }

    @Transactional
    public void deleteAgent(Long id) {
        Agent agent = agentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agente não encontrado com ID: " + id));

        if (!agent.getProspectedProperties().isEmpty()) {
            throw new BusinessException("Não é possível excluir um agente com propriedades vinculadas");
        }

        agentRepository.delete(agent);
    }

    @Transactional
    public AgentDTO registerAgent(AgentDTO agentDTO) {
        agentDTO.setHasAdminPermissions(false);
        return save(agentDTO);
    }

    private void validateAgent(Agent agent) {
        if (agentRepository.existsByCpf(agent.getCpf())) {
            throw new BusinessException("CPF já cadastrado");
        }
        if (agentRepository.existsByLicenseNumber(agent.getLicenseNumber())) {
            throw new BusinessException("Número de licença já cadastrado");
        }
    }

    private void updateExistingAgent(Agent existingAgent, AgentDTO agentDTO) {
        existingAgent.setName(agentDTO.getName());
        existingAgent.setCpf(agentDTO.getCpf());
        existingAgent.setRg(agentDTO.getRg());
        existingAgent.setEmail(agentDTO.getEmail());
        existingAgent.setPassword(agentDTO.getPassword());
        existingAgent.setPhone(agentDTO.getPhone());
        existingAgent.setAddress(agentDTO.getAddress());
        existingAgent.setRole(agentDTO.getRole());
        existingAgent.setLicenseNumber(agentDTO.getLicenseNumber());
        existingAgent.setRegistrationDate(agentDTO.getRegistrationDate());
        existingAgent.setHasAdminPermissions(agentDTO.getHasAdminPermissions());
        Set<Property> prospectedProperties = existingAgent.getProspectedProperties();
    }

    public boolean existsAnyAdmin() {
        return agentRepository.existsByHasAdminPermissionsTrue();
    }
}
