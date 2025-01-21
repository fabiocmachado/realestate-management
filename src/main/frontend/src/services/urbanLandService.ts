import api from './api';
import { Omit } from 'utility-types';
import { UrbanLandDTO, PaginatedResponse, Pageable } from '../types/models';

const API_URL = '/properties/urbanlands';

export const createUrbanLand = async (urbanLand: Omit<UrbanLandDTO, 'id' | 'propertyCode'>): Promise<UrbanLandDTO> => {
  try {
    const response = await api.post(API_URL, urbanLand);
    return response.data;
  } catch (error) {
    throw new Error('Error creating urban land: ' + error);
  }
}

export const getUrbanLandByCode = async (propertyCode: string): Promise<UrbanLandDTO> => {
  try {
    const response = await api.get(`${API_URL}/${propertyCode}`);
    return response.data;
  } catch (error) {
    throw new Error('Error fetching urban land: ' + error);
  }
};

export const getAllUrbanLands = async (pageable: Pageable): Promise<PaginatedResponse<UrbanLandDTO>> => {
  try {
    const response = await api.get(API_URL, { params: pageable });
    return response.data;
  } catch (error) {
    throw new Error('Error fetching urban lands: ' + error);
  }
};

export const updateUrbanLand = async (propertyCode: string, urbanLand: UrbanLandDTO): Promise<UrbanLandDTO> => {
  try {
    const response = await api.put(`${API_URL}/${propertyCode}`, urbanLand);
    return response.data;
  } catch (error) {
    throw new Error('Error updating urban land: ' + error);
  }
};

export const deleteUrbanLand = async (propertyCode: string): Promise<void> => {
  try {
    await api.delete(`${API_URL}/${propertyCode}`);
  } catch (error) {
    throw new Error('Error deleting urban land: ' + error);
  }
};
