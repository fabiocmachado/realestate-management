import api from './api';
import { Omit } from 'utility-types';
import { WarehouseDTO, PaginatedResponse, Pageable } from '../types/models';

const API_URL = '/properties/warehouses';

export const createWarehouse = async (warehouse: Omit<WarehouseDTO, 'id' | 'propertyCode'>): Promise<WarehouseDTO> => {
  try {
    const response = await api.post(API_URL, warehouse);
    return response.data;
  } catch (error) {
    throw new Error('Error creating Warehouse: ' + error);
  }
}

export const getWarehouseByCode = async (propertyCode: string): Promise<WarehouseDTO> => {
  try {
    const response = await api.get(`${API_URL}/${propertyCode}`);
    return response.data;
  } catch (error) {
    throw new Error('Error fetching Warehouse: ' + error);
  }
};

export const getAllWarehouses = async (pageable: Pageable): Promise<PaginatedResponse<WarehouseDTO>> => {
  try {
    const response = await api.get(API_URL, { params: pageable });
    return response.data;
  } catch (error) {
    throw new Error('Error fetching Warehouse: ' + error);
  }
};

export const updateWarehouse = async (propertyCode: string, warehouse: WarehouseDTO): Promise<WarehouseDTO> => {
  try {
    const response = await api.put(`${API_URL}/${propertyCode}`, warehouse);
    return response.data;
  } catch (error) {
    throw new Error('Error updating Warehouse: ' + error);
  }
};

export const deleteWarehouse = async (propertyCode: string): Promise<void> => {
  try {
    await api.delete(`${API_URL}/${propertyCode}`);
  } catch (error) {
    throw new Error('Error deleting Warehouse: ' + error);
  }
};
