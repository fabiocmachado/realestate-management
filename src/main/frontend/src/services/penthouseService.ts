import api from './api';
import { Omit } from 'utility-types';
import { PenthouseDTO, PaginatedResponse, Pageable } from '../types/models';

const API_URL = '/properties/penthouses';

export const createPenthouse = async (penthouse: Omit<PenthouseDTO, 'id' | 'propertyCode'>): Promise<PenthouseDTO> => {
  try {
    const response = await api.post(API_URL, penthouse);
    return response.data;
  } catch (error) {
    throw new Error('Error creating Penthouse: ' + error);
  }
}

export const getPenthouseByCode = async (propertyCode: string): Promise<PenthouseDTO> => {
  try {
    const response = await api.get(`${API_URL}/${propertyCode}`);
    return response.data;
  } catch (error) {
    throw new Error('Error fetching Penthouse: ' + error);
  }
};

export const getAllPenthouses = async (pageable: Pageable): Promise<PaginatedResponse<PenthouseDTO>> => {
  try {
    const response = await api.get(API_URL, { params: pageable });
    return response.data;
  } catch (error) {
    throw new Error('Error fetching Penthouses: ' + error);
  }
};

export const updatePenthouse = async (propertyCode: string, penthouse: PenthouseDTO): Promise<PenthouseDTO> => {
  try {
    const response = await api.put(`${API_URL}/${propertyCode}`, penthouse);
    return response.data;
  } catch (error) {
    throw new Error('Error updating Penthouse: ' + error);
  }
};

export const deletePenthouse = async (propertyCode: string): Promise<void> => {
  try {
    await api.delete(`${API_URL}/${propertyCode}`);
  } catch (error) {
    throw new Error('Error deleting Penthouse: ' + error);
  }
};
