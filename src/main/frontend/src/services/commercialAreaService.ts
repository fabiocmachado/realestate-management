import api from './api';
import { Omit } from 'utility-types';
import { CommercialAreaDTO, PaginatedResponse, Pageable } from '../types/models';

const API_URL = '/properties/commercialareas';

export const createCommercialArea = async (commercialarea: Omit<CommercialAreaDTO, 'id' | 'propertyCode'>): Promise<CommercialAreaDTO> => {
  try {
    const response = await api.post(API_URL, commercialarea);
    return response.data;
  } catch (error) {
    throw new Error('Error creating commercialarea: ' + error);
  }
}

export const getCommercialAreaByCode = async (propertyCode: string): Promise<CommercialAreaDTO> => {
  try {
    const response = await api.get(`${API_URL}/${propertyCode}`);
    return response.data;
  } catch (error) {
    throw new Error('Error fetching commercialarea: ' + error);
  }
};

export const getAllCommercialAreas = async (pageable: Pageable): Promise<PaginatedResponse<CommercialAreaDTO>> => {
  try {
    const response = await api.get(API_URL, { params: pageable });
    return response.data;
  } catch (error) {
    throw new Error('Error fetching commercialareas: ' + error);
  }
};

export const updateCommercialArea = async (propertyCode: string, commercialarea: CommercialAreaDTO): Promise<CommercialAreaDTO> => {
  try {
    const response = await api.put(`${API_URL}/${propertyCode}`, commercialarea);
    return response.data;
  } catch (error) {
    throw new Error('Error updating commercialarea: ' + error);
  }
};

export const deleteCommercialArea = async (propertyCode: string): Promise<void> => {
  try {
    await api.delete(`${API_URL}/${propertyCode}`);
  } catch (error) {
    throw new Error('Error deleting commercialarea: ' + error);
  }
};
