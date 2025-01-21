import api from './api';
import { Agent } from "../types/models"

const API_URL = "/agents";

export const getAgents = async () => {
  try {
    const response = await api.get(API_URL);
    return response.data;
  } catch (error) {
    console.error("Erro ao obter agentes:", error);
    throw error;
  }
};

export const getAgentById = async (id: number) => {
  try {
    const response = await api.get(`${API_URL}/${id}`);
    return response.data;
  } catch (error) {
    console.error("Erro ao obter agente:", error);
    throw error;
  }
};

export const createAgent = async (agent: { name: string, licenseNumber: string, email: string }) => {
  try {
    const response = await api.post(API_URL, agent);
    return response.data;
  } catch (error) {
    console.error("Erro ao salvar agente:", error);
    throw error;
  }
};

export const updateAgent = async (id: number, agent: Agent) => {
  const response = await api.put(`${API_URL}/${id}`, agent);
  return response.data;
};

export const deleteAgent = async (id: number) => {
  try {
    await api.delete(`${API_URL}/${id}`);
  } catch (error) {
    console.error("Erro ao deletar agente:", error);
    throw error;
  }
};

api.interceptors.response.use(
  (response) => response,
  async (error) => {
    if (error.response?.status === 403) {
      const token = localStorage.getItem("authToken");
      if (!token) {
        window.location.href = '/login';
        return Promise.reject(error);
      }
    }
    return Promise.reject(error);
  }
);