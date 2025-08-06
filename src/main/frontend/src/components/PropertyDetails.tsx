import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getPropertyDetails } from '../services/propertyService';
import { getAgentById } from '../services/agentService';
import { getSellerById } from '../services/sellerService';
import { useAuth } from "../contexts/AuthContext";

import {
  PropertyDTO,
  Agent,
  Seller,
} from '../types/models';

import ApartmentDetails from './details/ApartmentDetails';
import FarmDetails from './details/FarmDetails';
import UrbanLandDetails from './details/UrbanLandDetails';
import CountryHouseDetails from './details/CountryHouseDetails';
import PenthouseDetails from './details/PenthouseDetails';
import TownhouseDetails from './details/TownhouseDetails';
import CommercialBuildingDetails from './details/CommercialBuildingDetails';
import WarehouseDetails from './details/WarehouseDetails';
import CommercialAreaDetails from './details/CommercialAreaDetails';
import CommercialRoomDetails from './details/CommercialRoomDetails';
import HouseDetails from './details/HouseDetails';

const componentMap: Record<string, React.FC<any>> = {
  apartment: ApartmentDetails,
  penthouse: PenthouseDetails,
  townhouse: TownhouseDetails,
  house: HouseDetails,
  commercialbuilding: CommercialBuildingDetails,
  warehouse: WarehouseDetails,
  commercialarea: CommercialAreaDetails,
  farm: FarmDetails,
  countryhouse: CountryHouseDetails,
  urbanland: UrbanLandDetails,
  commercialroom: CommercialRoomDetails,
};

const EditButton: React.FC<{ onClick: () => void }> = ({ onClick }) => (
  <div className="mt-6 flex justify-end">
    <button
      onClick={onClick}
      className="bg-primary DEFAULT hover:bg-primary-dark text-white font-semibold py-2 px-4 rounded shadow transition-colors"
    >
      Editar Imóvel
    </button>
  </div>
);

const PropertyDetails: React.FC = () => {
  const { propertyCategory, propertyCode } = useParams();
  const [property, setProperty] = useState<PropertyDTO | null>(null);
  const [seller, setSeller] = useState<Seller | null>(null);
  const [agent, setAgent] = useState<Agent | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const navigate = useNavigate();
  const { user } = useAuth();

  useEffect(() => {
    const fetchPropertyDetails = async () => {
      if (!propertyCategory || !propertyCode) return;

      setLoading(true);
      setError(null);

      try {
        const propertyData = await getPropertyDetails(propertyCategory, propertyCode);
        setProperty(propertyData);

        const [sellerData, agentData] = await Promise.all([
          propertyData.sellerId ? getSellerById(propertyData.sellerId) : Promise.resolve(null),
          propertyData.agentId ? getAgentById(propertyData.agentId) : Promise.resolve(null),
        ]);

        setSeller(sellerData);
        setAgent(agentData);
      } catch (err) {
        console.error(err);
        setError("Erro ao carregar os detalhes da propriedade.");
      } finally {
        setLoading(false);
      }
    };

    fetchPropertyDetails();
  }, [propertyCategory, propertyCode]);

  const handleEditClick = () => {
    navigate(`/edit-property/${propertyCategory}/${propertyCode}`);
  };

  if (loading) return <div className="text-primary-light font-medium">Carregando...</div>;
  if (error) return <div className="text-red-600 font-semibold">{error}</div>;
  if (!property || !seller || !agent)
    return <div className="text-gray-600 italic">Detalhes do imóvel, vendedor e agente indisponíveis.</div>;

  const key = propertyCategory?.toLowerCase().replace(/_/g, '').trim() ?? '';
  const DetailsComponent = componentMap[key];

  if (!DetailsComponent) return <div className="text-red-500 font-bold">Tipo de imóvel não suportado</div>;

  return (
    <div className="max-w-5xl mx-auto p-6 bg-gray-light rounded-lg shadow-md">
          <section className="mb-8 grid grid-cols-1 md:grid-cols-1 gap-6">
        <div className="bg-white p-4 rounded shadow-sm">
          <DetailsComponent property={property} seller={seller} agent={agent} />
        </div>
      </section>
      {user?.role === "ADMIN" && <EditButton onClick={handleEditClick} />}
    </div>
  );
};

export default PropertyDetails;
