import api from './api';
import { Omit } from 'utility-types';
import { CountryHouseDTO, PaginatedResponse, Pageable } from '../types/models';

const API_URL = '/properties/countryhouses';

export const createCountryHouse = async (countryhouse: Omit<CountryHouseDTO, 'id' | 'propertyCode'>): Promise<CountryHouseDTO> => {
  try {
    const response = await api.post(API_URL, countryhouse);
    return response.data;
  } catch (error) {
    throw new Error('Error creating countryhouse: ' + error);
  }
}

export const getCountryHouseByCode = async (propertyCode: string): Promise<CountryHouseDTO> => {
  try {
    const response = await api.get(`${API_URL}/${propertyCode}`);
    return response.data;
  } catch (error) {
    throw new Error('Error fetching countryhouse: ' + error);
  }
};

export const getAllCountryHouses = async (pageable: Pageable): Promise<PaginatedResponse<CountryHouseDTO>> => {
  try {
    const response = await api.get(API_URL, { params: pageable });
    return response.data;
  } catch (error) {
    throw new Error('Error fetching countryhouses: ' + error);
  }
};

export const updateCountryHouse = async (propertyCode: string, countryhouse: CountryHouseDTO): Promise<CountryHouseDTO> => {
  try {
    const response = await api.put(`${API_URL}/${propertyCode}`, countryhouse);
    return response.data;
  } catch (error) {
    throw new Error('Error updating countryhouse: ' + error);
  }
};

export const deleteCountryHouse = async (propertyCode: string): Promise<void> => {
  try {
    await api.delete(`${API_URL}/${propertyCode}`);
  } catch (error) {
    throw new Error('Error deleting countryhouse: ' + error);
  }
};
