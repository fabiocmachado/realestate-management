import api from './api';
import { Omit } from 'utility-types';
import { CommercialRoomDTO, PaginatedResponse, Pageable } from '../types/models';

const API_URL = '/properties/commercialrooms';

export const createCommercialRoom = async (commercialroom: Omit<CommercialRoomDTO, 'id' | 'propertyCode'>): Promise<CommercialRoomDTO> => {
  try {
    const response = await api.post(API_URL, commercialroom);
    return response.data;
  } catch (error) {
    throw new Error('Error creating CommercialRoom: ' + error);
  }
}

export const getCommercialRoomByCode = async (propertyCode: string): Promise<CommercialRoomDTO> => {
  try {
    const response = await api.get(`${API_URL}/${propertyCode}`);
    return response.data;
  } catch (error) {
    throw new Error('Error fetching CommercialRoom: ' + error);
  }
};

export const getAllCommercialRoom = async (pageable: Pageable): Promise<PaginatedResponse<CommercialRoomDTO>> => {
  try {
    const response = await api.get(API_URL, { params: pageable });
    return response.data;
  } catch (error) {
    throw new Error('Error fetching CommercialRooms: ' + error);
  }
};

export const updateCommercialRoom = async (propertyCode: string, commercialroom: CommercialRoomDTO): Promise<CommercialRoomDTO> => {
  try {
    const response = await api.put(`${API_URL}/${propertyCode}`, commercialroom);
    return response.data;
  } catch (error) {
    throw new Error('Error updating CommercialRoom: ' + error);
  }
};

export const deleteCommercialRoom = async (propertyCode: string): Promise<void> => {
  try {
    await api.delete(`${API_URL}/${propertyCode}`);
  } catch (error) {
    throw new Error('Error deleting CommercialRoom: ' + error);
  }
};
