import api from './api';
import { PropertyDTO, PaginatedResponse, Pageable } from '../types/models';

const API_URL = "/properties";

export const getProperties = async (
  pageable: Pageable,
  status?: string,
  category?: string
): Promise<PaginatedResponse<PropertyDTO>> => {
  let url = API_URL;
  const params: Record<string, string> = {};

  if (status) params.status = status;
  if (category) params.category = category;

  const queryString = new URLSearchParams(params).toString();
  if (queryString) {
    url += `?${queryString}`;
  }

  try {
    const response = await api.get(url, {
      params: { page: pageable.page, size: pageable.size }
    });

    return {
      content: response.data.content,
      totalPages: response.data.totalPages,
      totalElements: response.data.totalElements,
      pageable: response.data.pageable,
      last: response.data.last,
      first: response.data.first,
      number: response.data.number,
      size: response.data.size,
      sort: response.data.sort,
      numberOfElements: response.data.numberOfElements,
      empty: response.data.empty,
    };
  } catch (error) {
    console.error('Erro ao buscar propriedades:', error);
    throw error;
  }
};


export const getPropertyByPropertyCode = async (propertyCode: string): Promise<PaginatedResponse<PropertyDTO>> => {
  try {
    const response = await api.get(`/properties/search?propertyCode=${propertyCode}`);
    return {
      content: [response.data],
      totalPages: 1,
      totalElements: 1,
      pageable: {
        pageNumber: 0,
        pageSize: 1,
      },
      last: true,
      first: true,
      number: 0,
      size: 1,
      sort: {
        sorted: false,
        unsorted: true,
        empty: true,
      },
      numberOfElements: 1,
      empty: false,
    };
  } catch (error) {
    console.error('Erro ao buscar imóvel:', error);
    throw error;
  }
};


export const getPropertyDetails = async (propertyType: string, propertyCode: string) => {
  const formatPropertyType = (type: string): string => type.toLowerCase().replace(/_/g, '') + 's';

  try {
    const url = `${API_URL}/${formatPropertyType(propertyType)}/${propertyCode}`;
    console.log('Request URL:', url);

    const response = await api.get(url);
    console.log('API Response:', response.data);
    return response.data;
  } catch (error) {
    console.error('Erro ao obter detalhes do imóvel:', error);
    throw error;
  }
};

