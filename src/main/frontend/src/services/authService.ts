import api from "./api";
import { LoginDTO, AuthResponseDTO } from "../types/models";

export const login = async (loginDTO: LoginDTO): Promise<AuthResponseDTO> => {
  try {
    const response = await api.post<AuthResponseDTO>("/auth/login", loginDTO);
    localStorage.setItem("authToken", response.data.token);
    return response.data;
  } catch (error: any) {
    const message =
      error.response?.data?.message || error.message || "Erro ao realizar login";
    throw new Error(message);
  }
};

export const validateToken = async (token: string): Promise<boolean> => {
  try {
    const response = await api.post(
      "/auth/validate",
      {},
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );
    return response.status === 200;
  } catch (error) {
    console.error("Erro ao validar o token:", error);
    return false;
  }
};
