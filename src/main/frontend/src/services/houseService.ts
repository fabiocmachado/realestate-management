import { HouseDTO } from '../types/models';
import api from './api';

export const createHouse = async (house: HouseDTO): Promise<HouseDTO> => {
  try {
    const response = await api.post('/properties/houses', house);
    return response.data;
  } catch (error) {
    throw new Error('Erro ao criar a casa: ' + error);
  }
};

export const getHouseByCode = async (propertyCode: string): Promise<HouseDTO> => {
  try {
    const response = await api.get(`/properties/houses/${propertyCode}`);
    return response.data;
  } catch (error) {
    throw new Error('Erro ao obter a casa: ' + error);
  }
};

export const getAllHouses = async (page: number, size: number): Promise<HouseDTO> => {
  try {
    const response = await api.get(`/properties/houses?page=${page}&size=${size}`);
    return response.data;
  } catch (error) {
    throw new Error('Erro ao obter todas as casas: ' + error);
  }
};

export const updateHouse = async (propertyCode: string, house: HouseDTO): Promise<HouseDTO> => {
  try {
    const response = await api.put(`/properties/houses/${propertyCode}`, house);
    return response.data;
  } catch (error) {
    throw new Error('Erro ao atualizar a casa: ' + error);
  }
};

export const deleteHouse = async (propertyCode: string): Promise<void> => {
  try {
    await api.delete(`/properties/houses/${propertyCode}`);
  } catch (error) {
    throw new Error('Erro ao excluir a casa: ' + error);
  }
};
