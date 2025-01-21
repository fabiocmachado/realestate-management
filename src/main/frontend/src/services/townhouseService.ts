import api from './api';
import { Omit } from 'utility-types';
import { TownhouseDTO , PaginatedResponse, Pageable } from '../types/models';

const API_URL = '/properties/townhouses';

export const createTownhouse = async (townhouse: Omit<TownhouseDTO, 'id' | 'propertyCode'>): Promise<TownhouseDTO> => {
  try {
    const response = await api.post(API_URL, townhouse);
    return response.data;
  } catch (error) {
    throw new Error('Error creating townhouse: ' + error);
  }
}

export const getTownhouseByCode = async (propertyCode: string): Promise<TownhouseDTO> => {
  try {
    const response = await api.get(`${API_URL}/${propertyCode}`);
    return response.data;
  } catch (error) {
    throw new Error('Error fetching townhouse: ' + error);
  }
};

export const getAllTownhouses = async (pageable: Pageable): Promise<PaginatedResponse<TownhouseDTO>> => {
  try {
    const response = await api.get(API_URL, { params: pageable });
    return response.data;
  } catch (error) {
    throw new Error('Error fetching townhouses: ' + error);
  }
};

export const updateTownhouse = async (propertyCode: string, townhouse: TownhouseDTO): Promise<TownhouseDTO> => {
  try {
    const response = await api.put(`${API_URL}/${propertyCode}`, townhouse);
    return response.data;
  } catch (error) {
    throw new Error('Error updating townhouse: ' + error);
  }
};

export const deleteTownhouse = async (propertyCode: string): Promise<void> => {
  try {
    await api.delete(`${API_URL}/${propertyCode}`);
  } catch (error) {
    throw new Error('Error deleting townhouse: ' + error);
  }
};
