package com.realestate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.realestate.dto.AgentDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AgentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testListAllAgents() throws Exception {
        mockMvc.perform(get("/agents"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testCreateAgent() throws Exception {
        // Dados do agente a ser criado
        AgentDTO agentDTO = new AgentDTO();
        agentDTO.setName("John Doe");

        mockMvc.perform(post("/agents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(agentDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void testGetAgentById() throws Exception {
        // Criar um agente para testar a busca
        AgentDTO agentDTO = new AgentDTO();
        agentDTO.setName("Jane Doe");

        String response = mockMvc.perform(post("/agents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(agentDTO)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        AgentDTO createdAgent = objectMapper.readValue(response, AgentDTO.class);

        // Buscar o agente criado
        mockMvc.perform(get("/agents/{id}", createdAgent.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Jane Doe"));
    }

    @Test
    void testDeleteAgent() throws Exception {
        // Criar um agente para testar a exclusão
        AgentDTO agentDTO = new AgentDTO();
        agentDTO.setName("Agent to Delete");

        String response = mockMvc.perform(post("/agents")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(agentDTO)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        AgentDTO createdAgent = objectMapper.readValue(response, AgentDTO.class);

        // Excluir o agente criado
        mockMvc.perform(delete("/agents/{id}", createdAgent.getId()))
                .andExpect(status().isNoContent());
    }
}
