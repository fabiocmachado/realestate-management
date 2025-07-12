import axios, { AxiosError } from "axios";
import { LoginDTO, AuthResponseDTO } from "../types/models";

const API_URL = "http://localhost:8080";

const api = axios.create({
  baseURL: API_URL,
  headers: {
    "Content-Type": "application/json",
  },
  withCredentials: true,
});

api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("authToken");
    if (token) {
      config.headers["Authorization"] = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

api.interceptors.response.use(
  (response) => response,
  (error) => {
    console.log('Error details:', {
      status: error.response?.status,
      data: error.response?.data,
      config: error.config,
      headers: error.response?.headers,
    });

    if (error.response?.status === 401 || error.response?.status === 403) {
      const authError =
        error.response?.data?.error === "Unauthorized" ||
        error.response?.data?.error === "Forbidden";

      if (authError) {
        localStorage.removeItem("authToken");
        window.location.href = "/login";
      }
    }

    if (error.response?.status === 500) {
      console.error("Erro interno do servidor. Tente novamente mais tarde.");
    }

    return Promise.reject(error);
  }
);

export const login = async (loginDTO: LoginDTO): Promise<AuthResponseDTO> => {
  try {
    const response = await api.post<AuthResponseDTO>("/auth/login", loginDTO);
    localStorage.setItem("authToken", response.data.token);
    return response.data;
  } catch (error) {
    if (error instanceof AxiosError && error.response) {
      throw new Error(
        `Erro ao realizar login: ${
          error.response.data.message || error.message
        }`
      );
    }
    throw new Error(
      "Erro ao realizar login: " + (error instanceof Error ? error.message : error)
    );
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
    if (error instanceof AxiosError) {
      console.error("Erro ao validar o token:", error.response?.data);
    }
    console.error(
      "Erro ao validar o token:",
      error instanceof Error ? error.message : error
    );
    return false;
  }
};

export default api;
