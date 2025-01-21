import axios, { AxiosError } from "axios";
import { LoginDTO } from "../types/models";
import { AuthResponseDTO } from "../types/models";

const API_URL = "http://localhost:8080/auth";

export const login = async (loginDTO: LoginDTO): Promise<AuthResponseDTO> => {
  try {
    const response = await axios.post<AuthResponseDTO>(`${API_URL}/login`, loginDTO);
    localStorage.setItem("authToken", response.data.token);
    return response.data;
  } catch (error) {
    if (error instanceof AxiosError && error.response) {
      throw new Error(`Erro ao realizar login: ${error.response.data.message || error.message}`);
    }
    throw new Error("Erro ao realizar login: " + (error instanceof Error ? error.message : error));
  }
};

export const validateToken = async (token: string): Promise<boolean> => {
  try {
    const response = await axios.post(`${API_URL}/validate`, {}, {
      headers: {
        Authorization: `Bearer ${token}`,
      }
    });
    return response.status === 200;
  } catch (error) {
    if (error instanceof AxiosError) {
      console.error("Erro ao validar o token:", error.response?.data);
    }
    console.error("Erro ao validar o token:", error instanceof Error ? error.message : error);
    return false;
  }
};
