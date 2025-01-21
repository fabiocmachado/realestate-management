import api from './api';
import { Omit } from 'utility-types';
import { CommercialBuildingDTO, PaginatedResponse, Pageable } from '../types/models';

const API_URL = '/properties/commercialbuildings';

export const createCommercialBuilding = async (commercialbuilding: Omit<CommercialBuildingDTO, 'id' | 'propertyCode'>): Promise<CommercialBuildingDTO> => {
  try {
    const response = await api.post(API_URL, commercialbuilding);
    return response.data;
  } catch (error) {
    throw new Error('Error creating CommercialBuilding: ' + error);
  }
}

export const getCommercialBuildingByCode = async (propertyCode: string): Promise<CommercialBuildingDTO> => {
  try {
    const response = await api.get(`${API_URL}/${propertyCode}`);
    return response.data;
  } catch (error) {
    throw new Error('Error fetching CommercialBuilding: ' + error);
  }
};

export const getAllCommercialBuildings = async (pageable: Pageable): Promise<PaginatedResponse<CommercialBuildingDTO>> => {
  try {
    const response = await api.get(API_URL, { params: pageable });
    return response.data;
  } catch (error) {
    throw new Error('Error fetching CommercialBuildings: ' + error);
  }
};

export const updateCommercialBuilding = async (propertyCode: string, commercialbuilding: CommercialBuildingDTO): Promise<CommercialBuildingDTO> => {
  try {
    const response = await api.put(`${API_URL}/${propertyCode}`, commercialbuilding);
    return response.data;
  } catch (error) {
    throw new Error('Error updating CommercialBuilding: ' + error);
  }
};

export const deleteCommercialBuilding = async (propertyCode: string): Promise<void> => {
  try {
    await api.delete(`${API_URL}/${propertyCode}`);
  } catch (error) {
    throw new Error('Error deleting CommercialBuilding: ' + error);
  }
};
