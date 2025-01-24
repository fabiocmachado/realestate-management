import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { PropertyCategory, PropertyCategoryDescription } from '../types/models';
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
import '../styles/addProperty.css'

interface CommonFormProps {
  onSuccess: () => void;
  onCancel: () => void;
}

const formComponents: {
  [key in PropertyCategory]: React.FC<CommonFormProps>
} = {
  URBAN_LAND: UrbanLandForm as React.FC<CommonFormProps>,
  APARTMENT: ApartmentForm as React.FC<CommonFormProps>,
  PENTHOUSE: PenthouseForm as React.FC<CommonFormProps>,
  HOUSE: HouseForm as React.FC<CommonFormProps>,
  TOWNHOUSE: TownhouseForm as React.FC<CommonFormProps>,
  WAREHOUSE: WarehouseForm as React.FC<CommonFormProps>,
  COMMERCIAL_BUILDING: CommercialBuildingForm as React.FC<CommonFormProps>,
  FARM: FarmForm as React.FC<CommonFormProps>,
  COUNTRY_HOUSE: CountryHouseForm as React.FC<CommonFormProps>,
  COMMERCIAL_AREA: CommercialAreaForm as React.FC<CommonFormProps>,
  COMMERCIAL_ROOM: CommercialRoomForm as React.FC<CommonFormProps>
};

const AddProperty: React.FC = () => {
  const [propertyType, setPropertyType] = useState<PropertyCategory | null>(null);
  const navigate = useNavigate();

  const handlePropertyTypeChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setPropertyType(e.target.value as PropertyCategory);
  };

  const handleSuccess = () => {
    alert('Propriedade adicionada com sucesso!');
    navigate(`/properties`);
  };

  const handleCancel = () => {
    console.log('Formulário cancelado');
    setPropertyType(null);
  };

  return (
    <div className="form-container">
      <h1>Cadastrar Propriedade</h1>
      <div className="select-wrapper">
        <select
          className="property-select"
          value={propertyType || ''}
          onChange={handlePropertyTypeChange}
        >
          <option value="" disabled>
            Escolha a categoria do imóvel
          </option>
          <optgroup label="RESIDENCIAIS">
            <option value="APARTMENT">Apartamento</option>
            <option value="PENTHOUSE">Cobertura</option>
            <option value="HOUSE">Casa</option>
            <option value="TOWNHOUSE">Sobrado</option>
            <option value="URBAN_LAND">Terreno</option>
          </optgroup>
          <optgroup label="COMERCIAIS">
            <option value="COMMERCIAL_AREA">Área comercial</option>
            <option value="WAREHOUSE">Galpão</option>
            <option value="COMMERCIAL_BUILDING">Prédio Comercial</option>
            <option value="COMMERCIAL_ROOM">Sala Comercial</option>
          </optgroup>
          <optgroup label="RURAIS">
            <option value="COUNTRY_HOUSE">Chácara</option>
            <option value="FARM">Fazenda</option>
          </optgroup>
        </select>
      </div>

      {propertyType &&
        React.createElement(formComponents[propertyType as PropertyCategory], {
          onSuccess: handleSuccess,
          onCancel: handleCancel,
        })}
    </div>

  );

};

export default AddProperty;