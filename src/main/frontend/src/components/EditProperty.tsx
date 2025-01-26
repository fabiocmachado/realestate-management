import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import UrbanLandForm from './forms/UrbanLandForm';
import ApartmentForm from './forms/ApartmentForm';
import PenthouseForm from './forms/PenthouseForm';
import HouseForm from './forms/HouseForm';
import TownhouseForm from './forms/TownhouseForm';
import WarehouseForm from './forms/WarehouseForm';
import CommercialBuildingForm from './forms/CommercialBuildingForm';
import FarmForm from './forms/FarmForm';
import CountryHouseForm from './forms/CountryHouseForm';
import CommercialAreaForm from './forms/CommercialAreaForm';
import CommercialRoomForm from './forms/CommercialRoomForm';
import { getUrbanLandByCode } from '../services/urbanLandService';
import { getApartmentByCode } from '../services/apartmentService';
import { getPenthouseByCode } from '../services/penthouseService';
import { getHouseByCode } from '../services/houseService';
import { getTownhouseByCode } from '../services/townhouseService';
import { getWarehouseByCode } from '../services/warehouseService';
import { getCommercialBuildingByCode } from '../services/commercialBuildingService';
import { getFarmByCode } from '../services/farmService';
import { getCountryHouseByCode } from '../services/countryHouseService';
import { getCommercialAreaByCode } from '../services/commercialAreaService';
import { getCommercialRoomByCode } from '../services/commercialRoomService';
import { PropertyCategory, UrbanLandDTO, ApartmentDTO, PenthouseDTO, HouseDTO, TownhouseDTO, WarehouseDTO, FarmDTO, CountryHouseDTO, CommercialBuildingDTO, CommercialRoomDTO, CommercialAreaDTO } from '../types/models';

const EditProperty: React.FC = () => {
  const { propertyCode } = useParams<{ propertyCode: string }>();
  const navigate = useNavigate();
  const [propertyType, setPropertyType] = useState<PropertyCategory>('URBAN_LAND');
  const [propertyData, setPropertyData] = useState<UrbanLandDTO | ApartmentDTO | PenthouseDTO | HouseDTO | TownhouseDTO | WarehouseDTO | CommercialBuildingDTO | FarmDTO | CountryHouseDTO | CommercialAreaDTO | CommercialRoomDTO | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchProperty = async () => {
      if (!propertyCode) {
        setError('Código da propriedade não fornecido');
        setIsLoading(false);
        return;
      }

      try {
        setIsLoading(true);
        const propertyTypes: PropertyCategory[] = ['URBAN_LAND', 'APARTMENT', 'PENTHOUSE', 'HOUSE', 'TOWNHOUSE', 'WAREHOUSE', 'COMMERCIAL_BUILDING', 'FARM', 'COUNTRY_HOUSE', 'COMMERCIAL_AREA', 'COMMERCIAL_ROOM'];
        const propertyServices = [getUrbanLandByCode, getApartmentByCode, getPenthouseByCode, getHouseByCode, getTownhouseByCode, getWarehouseByCode, getCommercialBuildingByCode, getFarmByCode, getCountryHouseByCode, getCommercialAreaByCode, getCommercialRoomByCode];

        const fetchedData = await Promise.any(
          propertyTypes.map((type, index) =>
            propertyServices[index](propertyCode).then(data => ({ type, data }))
          )
        );

        setPropertyData(fetchedData.data);
        setPropertyType(fetchedData.type as PropertyCategory);
      } catch {
        setError('Imóvel não encontrado');
      } finally {
        setIsLoading(false);
      }
    };

    fetchProperty();
  }, [propertyCode]);

  const handleSuccess = () => {
    alert('Propriedade editada com sucesso!');
    const redirectPath = `/properties/${propertyType}/${propertyCode}`;
    navigate(redirectPath);
  };

  const handleCancel = () => {
    navigate(-1);
  };

  if (isLoading) return <div className="loading">Carregando dados da propriedade...</div>;
  if (error) return <div className="error">{error}</div>;

  const formComponents: {
    [key in PropertyCategory]: React.FC<any>
  } = {
    URBAN_LAND: UrbanLandForm,
    APARTMENT: ApartmentForm,
    PENTHOUSE: PenthouseForm,
    HOUSE: HouseForm,
    TOWNHOUSE: TownhouseForm,
    WAREHOUSE: WarehouseForm,
    COMMERCIAL_BUILDING: CommercialBuildingForm,
    FARM: FarmForm,
    COUNTRY_HOUSE: CountryHouseForm,
    COMMERCIAL_AREA: CommercialAreaForm,
    COMMERCIAL_ROOM: CommercialRoomForm
  };

  const FormComponent = formComponents[propertyType];
  if (!FormComponent) return <div className="error">Tipo de propriedade não suportado para edição</div>;

  return (
    <div className="form-container">
      <h2>Código: {propertyCode}</h2>
      {propertyData &&
        <FormComponent
          initialData={propertyData}
          onSuccess={handleSuccess}
          onCancel={handleCancel}
        />
      }
    </div>
  );
};

export default EditProperty;