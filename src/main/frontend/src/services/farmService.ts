import api from './api';
import { Omit } from 'utility-types';
import { FarmDTO, PaginatedResponse, Pageable } from '../types/models';

const API_URL = '/properties/farms';

export const createFarm = async (farm: Omit<FarmDTO, 'id' | 'propertyCode'>): Promise<FarmDTO> => {
  try {
    const response = await api.post(API_URL, farm);
    return response.data;
  } catch (error) {
    throw new Error('Error creating farm: ' + error);
  }
}

export const getFarmByCode = async (propertyCode: string): Promise<FarmDTO> => {
  try {
    const response = await api.get(`${API_URL}/${propertyCode}`);
    return response.data;
  } catch (error) {
    throw new Error('Error fetching farm: ' + error);
  }
};

export const getAllFarms = async (pageable: Pageable): Promise<PaginatedResponse<FarmDTO>> => {
  try {
    const response = await api.get(API_URL, { params: pageable });
    return response.data;
  } catch (error) {
    throw new Error('Error fetching farms: ' + error);
  }
};

export const updateFarm = async (propertyCode: string, farm: FarmDTO): Promise<FarmDTO> => {
  try {
    const response = await api.put(`${API_URL}/${propertyCode}`, farm);
    return response.data;
  } catch (error) {
    throw new Error('Error updating farm: ' + error);
  }
};

export const deleteFarm = async (propertyCode: string): Promise<void> => {
  try {
    await api.delete(`${API_URL}/${propertyCode}`);
  } catch (error) {
    throw new Error('Error deleting farm: ' + error);
  }
};
