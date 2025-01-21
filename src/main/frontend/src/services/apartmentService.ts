import api from './api';
import { ApartmentDTO, PaginatedResponse, Pageable } from '../types/models';

const apiRequest = async <T>(url: string, method: 'get' | 'post' | 'put' | 'delete', data?: T): Promise<T> => {
  try {
    const response = await api({
      method,
      url,
      data,
    });
    return response.data;
  } catch (error) {
    throw new Error(`Error in API request: ${error}`);
  }
};

export const createApartment = async (apartment: ApartmentDTO): Promise<ApartmentDTO> => {
  return apiRequest<ApartmentDTO>('/properties/apartments', 'post', apartment);
};

export const getApartmentByCode = async (propertyCode: string): Promise<ApartmentDTO> => {
  return apiRequest<ApartmentDTO>(`/properties/apartments/${propertyCode}`, 'get');
};

export const getAllApartments = async (pageable: Pageable): Promise<PaginatedResponse<ApartmentDTO>> => {
  try {
    const response = await api.get<PaginatedResponse<ApartmentDTO>>('/properties/apartments', {
      params: pageable,
    });
    return response.data;
  } catch (error) {
    console.error("Erro ao buscar apartamentos:", error);
    throw error;
  }
};


export const updateApartment = async (propertyCode: string, apartment: ApartmentDTO): Promise<ApartmentDTO> => {
  return apiRequest<ApartmentDTO>(`/properties/apartments/${propertyCode}`, 'put', apartment);
};

export const deleteApartment = async (propertyCode: string): Promise<void> => {
  return apiRequest<void>(`/properties/apartments/${propertyCode}`, 'delete');
};
