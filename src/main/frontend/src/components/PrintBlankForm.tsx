import React from 'react';
import { useParams } from 'react-router-dom';
import '../styles/blankForm.css';
import { ApartmentBlankForm } from './blankForms/ApartmentBlankForm';
import { HouseBlankForm } from './blankForms/HouseBlankForm';
import { PenthouseBlankForm } from './blankForms/PenthouseBlankForm';
import { TownhouseBlankForm } from './blankForms/TownhouseBlankForm';
import { WarehouseBlankForm } from './blankForms/WarehouseBlankForm';
import { CommercialBuildingBlankForm } from './blankForms/CommercialBuildingBlankForm';
import { FarmBlankForm } from './blankForms/FarmBlankForm';
import { CountryHouseBlankForm } from './blankForms/CountryHouseBlankForm';
import { UrbanLandBlankForm } from './blankForms/UrbanLandBlankForm';
import { CommercialAreaBlankForm } from './blankForms/CommercialAreaBlankForm';
import { CommercialRoomBlankForm } from './blankForms/CommercialRoomBlankForm';

const FORM_COMPONENTS: Record<string, React.FC> = {
  APARTMENT: ApartmentBlankForm,
  HOUSE: HouseBlankForm,
  PENTHOUSE: PenthouseBlankForm,
  TOWNHOUSE: TownhouseBlankForm,
  WAREHOUSE: WarehouseBlankForm,
  COMMERCIAL_BUILDING: CommercialBuildingBlankForm,
  FARM: FarmBlankForm,
  COUNTRY_HOUSE: CountryHouseBlankForm,
  URBAN_LAND: UrbanLandBlankForm,
  COMMERCIAL_AREA: CommercialAreaBlankForm,
  COMMERCIAL_ROOM: CommercialRoomBlankForm,
};

interface PrintBlankFormProps {
  propertyCategory: string;
}

const PrintBlankForm: React.FC = () => {
  const { propertyCategory } = useParams<{ propertyCategory: string }>();

  if (!propertyCategory) {
    return (
      <div className="print-only">
        <h1 className="heading">Erro</h1>
        <p>O parâmetro "propertyCategory" está ausente.</p>
      </div>
    );
  }

  const FormComponent = FORM_COMPONENTS[propertyCategory.toUpperCase()];

  if (!FormComponent) {
    return (
      <div className="print-only">
        <h1 className="heading">Tipo de imóvel não suportado</h1>
        <p>Por favor, escolha um tipo de imóvel válido.</p>
      </div>
    );
  }

  return <FormComponent />;
};

export default PrintBlankForm;
