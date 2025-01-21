import api from './api';
import {Seller} from "../types/models"


const API_URL = "/sellers";

export const getSellers = async (): Promise<Seller[]> => {
  try {
    const response = await api.get<Seller[]>(API_URL);
    return response.data;
  } catch (error) {
    console.error("Erro ao obter vendedores:", error);
    throw error;
  }
};

export const getSellerById = async (id: number): Promise<Seller> => {
  try {
    const response = await api.get<Seller>(`${API_URL}/${id}`);
    return response.data;
  } catch (error) {
    console.error("Erro ao obter vendedor:", error);
    throw error;
  }
};

export const createSeller = async (data: Omit<Seller, "id">): Promise<Seller> => {
  try {
    const response = await api.post<Seller>(API_URL, data);
    return response.data;
  } catch (error) {
    console.error("Erro ao salvar vendedor:", error);
    throw error;
  }
};


export const updateSeller = async (id: number, data: Seller): Promise<Seller> => {
  try {
    const response = await api.put<Seller>(`${API_URL}/${id}`, data);
    return response.data;
  } catch (error) {
    console.error("Erro ao atualizar vendedor:", error);
    throw error;
  }
};

export const deleteSeller = async (id: number): Promise<void> => {
  try {
    await api.delete(`${API_URL}/${id}`);
  } catch (error: any) {
      if (error.response && error.response.data && error.response.data.message) {
        throw new Error(error.response.data.message);
      } else {
        throw new Error("Erro desconhecido ao excluir o vendedor.");
      }
    }
};

api.interceptors.response.use(
  (response) => response,
  async (error) => {
    if (error.response?.status === 401 || error.response?.status === 403) {
      const token = localStorage.getItem("authToken");
      if (!token) {
        window.location.href = '/login';
        return Promise.reject(error);
      }
    }
    return Promise.reject(error);
  }
);
