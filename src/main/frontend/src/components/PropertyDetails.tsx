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
      <header className="mb-6 border-b border-gray-pale pb-4">
        <h1 className="text-3xl font-bold text-primary-dark">
          Detalhes do Imóvel - {property.propertyCode}
        </h1>
        <p className="text-gray-medium mt-1">Categoria: {propertyCategory?.toUpperCase()}</p>
      </header>

      <section className="mb-8 grid grid-cols-1 md:grid-cols-3 gap-6">
        <div className="bg-white p-4 rounded shadow-sm">
          <h3 className="text-lg font-semibold mb-2">Características Gerais</h3>
          <p><span className="font-semibold">Código:</span> {property.propertyCode}</p>
          <p><span className="font-semibold">Tipo:</span> {propertyCategory}</p>
          <p><span className="font-semibold">Área (m²):</span> {property.area}</p>
          <p><span className="font-semibold">Preço:</span> R$ {property.price?.toLocaleString()}</p>
        </div>

        <div className="bg-white p-4 rounded shadow-sm">
          <h3 className="text-lg font-semibold mb-2">Localização</h3>
          <p><span className="font-semibold">Cidade:</span> {property.city}</p>
          <p><span className="font-semibold">Bairro:</span> {property.neighborhood}</p>
          <p><span className="font-semibold">Endereço:</span> {property.address}</p>
          <p><span className="font-semibold">CEP:</span> {property.zipCode}</p>
        </div>

        <div className="bg-white p-4 rounded shadow-sm">
          <h3 className="text-lg font-semibold mb-2">Detalhes Adicionais</h3>
          <DetailsComponent property={property} seller={seller} agent={agent} />
        </div>
      </section>

      <section className="mb-8 grid grid-cols-1 md:grid-cols-2 gap-6">
        <div className="p-4 bg-white border border-gray-pale rounded shadow-sm">
          <h2 className="text-xl font-semibold text-primary DEFAULT mb-2">Informações do Vendedor</h2>
          <p><span className="font-semibold">Nome:</span> {seller.name}</p>
          <p><span className="font-semibold">Telefone:</span> {seller.phone}</p>
          <p><span className="font-semibold">Email:</span> {seller.email}</p>
        </div>
        <div className="p-4 bg-white border border-gray-pale rounded shadow-sm">
          <h2 className="text-xl font-semibold text-primary DEFAULT mb-2">Informações do Agente</h2>
          <p><span className="font-semibold">Nome:</span> {agent.name}</p>
          <p><span className="font-semibold">Telefone:</span> {agent.phone}</p>
          <p><span className="font-semibold">Email:</span> {agent.email}</p>
        </div>
      </section>

      {user?.role === "ADMIN" && <EditButton onClick={handleEditClick} />}
    </div>
  );
};

export default PropertyDetails;
