package com.realestate.service;

import com.realestate.dto.AgentDTO;
import com.realestate.entity.person.Agent;
import com.realestate.exception.ResourceNotFoundException;
import com.realestate.repository.AgentRepository;
import com.realestate.service.AgentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AgentServiceTest {

    @Mock
    private AgentRepository agentRepository;

    @InjectMocks
    private AgentService agentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterAgent() {
        // Configurando os dados de entrada e saída
        AgentDTO agentDTO = new AgentDTO();
        agentDTO.setName("John Doe");

        Agent agent = new Agent();
        agent.setName("John Doe");

        when(agentRepository.save(any(Agent.class))).thenReturn(agent);

        // Chama o método a ser testado
        AgentDTO result = agentService.registerAgent(agentDTO);

        // Verificações
        assertNotNull(result);
        assertEquals("John Doe", result.getName());
        verify(agentRepository, times(1)).save(any(Agent.class));
    }

    @Test
    void testFindAgentByIdWhenExists() {
        // Configurando o mock
        Agent agent = new Agent();
        agent.setId(1L);
        agent.setName("John Doe");

        when(agentRepository.findById(1L)).thenReturn(Optional.of(agent));

        // Chama o método a ser testado
        Optional<AgentDTO> result = agentService.findAgentById(1L);

        // Verificações
        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getName());
        verify(agentRepository, times(1)).findById(1L);
    }

    @Test
    void testFindAgentByIdWhenNotExists() {
        // Configurando o mock
        when(agentRepository.findById(1L)).thenReturn(Optional.empty());

        // Chama o método a ser testado
        Optional<AgentDTO> result = agentService.findAgentById(1L);

        // Verificações
        assertFalse(result.isPresent());
        verify(agentRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteAgent() {
        // Configurando o mock
        Agent agent = new Agent();
        agent.setId(1L);

        when(agentRepository.findById(1L)).thenReturn(Optional.of(agent));
        doNothing().when(agentRepository).delete(agent);

        // Chama o método a ser testado
        agentService.deleteAgent(1L);

        // Verificações
        verify(agentRepository, times(1)).findById(1L);
        verify(agentRepository, times(1)).delete(agent);
    }

    @Test
    void testDeleteAgentWhenNotExists() {
        // Configurando o mock
        when(agentRepository.findById(1L)).thenReturn(Optional.empty());

        // Chama o método e espera uma exceção
        assertThrows(ResourceNotFoundException.class, () -> agentService.deleteAgent(1L));

        // Verificações
        verify(agentRepository, times(1)).findById(1L);
        verify(agentRepository, never()).delete(any());
    }
}
