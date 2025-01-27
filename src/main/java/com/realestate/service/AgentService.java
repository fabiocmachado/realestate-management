package com.realestate.service;

import com.realestate.dto.AgentDTO;
import com.realestate.entity.person.Agent;
import com.realestate.exception.BusinessException;
import com.realestate.repository.AgentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.realestate.mapper.AgentMapper;
import com.realestate.enums.UserRole;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AgentService {

    private final AgentRepository agentRepository;
    private final PasswordEncoder passwordEncoder;
    private final AgentMapper agentMapper;

    @Transactional
    public AgentDTO createFirstAdmin(AgentDTO agentDTO) {
        agentDTO.setHasAdminPermissions(true);
        return saveAgent(agentDTO);
    }

    @Transactional(readOnly = true)
    public List<AgentDTO> findAllAgents() {
        List<Agent> agents = agentRepository.findAllByOrderByIdAsc();
        return agents != null ? agents.stream()
                .map(agent -> {
                    AgentDTO agentDTO = agentMapper.toDTO(agent);
                    agentDTO.setPassword(null);
                    return agentDTO;
                })
                .collect(Collectors.toList())
                : Collections.emptyList();
    }

    @Transactional(readOnly = true)
    public Optional<AgentDTO> findAgentById(Long id) {
        return agentRepository.findById(id)
                .map(agent -> {
                    AgentDTO agentDTO = agentMapper.toDTO(agent);
                    agentDTO.setPassword(null);
                    return agentDTO;
                });
    }

    @Transactional
    public AgentDTO registerAgent(AgentDTO agentDTO) {
        agentDTO.setHasAdminPermissions(UserRole.ADMIN == agentDTO.getRole());
        return saveAgent(agentDTO);
    }

    @Transactional
    public AgentDTO updateAgent(Long id, AgentDTO agentDTO) {
        Agent agent = agentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agente não encontrado com ID: " + id));
        if (agentDTO.getPassword() != null && !agentDTO.getPassword().isEmpty()) {
            agent.setPassword(passwordEncoder.encode(agentDTO.getPassword()));
        }
        if (agentDTO.getRole() != null) {
            agent.setRole(agentDTO.getRole());
            agent.setHasAdminPermissions(agentDTO.getRole() == UserRole.ADMIN);
        }
        agentMapper.updateEntityFromDTO(agentDTO, agent);

        Agent updatedAgent = agentRepository.save(agent);
        return agentMapper.toDTO(updatedAgent);
    }

    @Transactional
    public void deleteAgent(Long id) {
        Agent agent = agentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agente não encontrado com ID: " + id));

        agentRepository.delete(agent);
    }

    @Transactional
    private AgentDTO saveAgent(AgentDTO agentDTO) {
        Agent agent = agentMapper.toEntity(agentDTO);
        validateAgent(agent);

        String encodedPassword = passwordEncoder.encode(agentDTO.getPassword());
        agent.setPassword(encodedPassword);

        Agent savedAgent = agentRepository.save(agent);
        return agentMapper.toDTO(savedAgent);
    }

    private void validateAgent(Agent agent) {
        if (agentRepository.existsByCpf(agent.getCpf())) {
            throw new BusinessException("CPF já cadastrado");
        }
        if (agentRepository.existsByLicenseNumber(agent.getLicenseNumber())) {
            throw new BusinessException("Número de licença já cadastrado");
        }
    }

}
