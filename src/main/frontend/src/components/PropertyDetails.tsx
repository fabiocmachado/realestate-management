import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getPropertyDetails } from '../services/propertyService';
import { getAgentById } from '../services/agentService';
import { getSellerById } from '../services/sellerService';
import { FarmDetails } from './details/FarmDetails';
import { UrbanLandDetails } from './details/UrbanLandDetails';
import { ApartmentDetails } from './details/ApartmentDetails';
import { CountryHouseDetails } from './details/CountryHouseDetails';
import { PenthouseDetails } from './details/PenthouseDetails';
import { TownhouseDetails } from './details/TownhouseDetails';
import { CommercialBuildingDetails } from './details/CommercialBuildingDetails';
import { WarehouseDetails } from './details/WarehouseDetails';
import { CommercialAreaDetails } from './details/CommercialAreaDetails';
import { CommercialRoomDetails } from './details/CommercialRoomDetails';
import { HouseDetails } from './details/HouseDetails';
import { useAuth } from "../contexts/AuthContext";
import { PropertyDTO, FarmDTO, CountryHouseDTO, Agent, Seller, UrbanLandDTO, ApartmentDTO, TownhouseDTO, PenthouseDTO, CommercialBuildingDTO, WarehouseDTO, CommercialAreaDTO, HouseDTO, CommercialRoomDTO } from '../types/models';
import "../styles/propertyDetails.css";

const PropertyDetails = () => {
  const { propertyCategory, propertyCode } = useParams();
  const [property, setProperty] = useState<PropertyDTO | null>(null);
  const [seller, setSeller] = useState<Seller | null>(null);
  const [agent, setAgent] = useState<Agent | null>(null);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();
  const { user } = useAuth();


  useEffect(() => {
    const fetchPropertyDetails = async () => {
      if (propertyCategory && propertyCode) {
        try {
          const propertyData = await getPropertyDetails(propertyCategory, propertyCode);
          setProperty(propertyData);

          const sellerId = propertyData.sellerId;
          const agentId = propertyData.agentId;

          const [sellerData, agentData] = await Promise.all([
            sellerId ? getSellerById(sellerId) : Promise.resolve(null),
            agentId ? getAgentById(agentId) : Promise.resolve(null),
          ]);

          setSeller(sellerData);
          setAgent(agentData);
        } catch (error) {
          console.error("Erro ao carregar os detalhes da propriedade:", error);
        } finally {
          setLoading(false);
        }
      }
    };

    fetchPropertyDetails();
  }, [propertyCategory, propertyCode]);

  const renderDetails = () => {
    if (loading) {
      return <div>Carregando...</div>;
    }

    if (!property || !seller || !agent) {
      return <div>Carregando detalhes do imóvel, vendedor e agente...</div>;
    }

    const handleEditClick = () => {
      navigate(`/edit-property/${propertyCategory}/${propertyCode}`);
    };

    const formattedCategory = propertyCategory?.toLowerCase().replace(/_/g, '').trim();
     switch (formattedCategory) {
      case 'apartment':
        return (
          <div>
            <ApartmentDetails property={property as ApartmentDTO} seller={seller} agent={agent} />
            {user?.role === "ADMIN" && (
              <div className="edit-button-container">
                <button className="edit-button" onClick={handleEditClick}>Editar Imóvel</button>
              </div>
            )}
          </div>
        );
      case 'penthouse':
        return (
          <div>
            <PenthouseDetails property={property as PenthouseDTO} seller={seller} agent={agent} />
            {user?.role === "ADMIN" && (
              <div className="edit-button-container">
                <button className="edit-button" onClick={handleEditClick}>Editar Imóvel</button>
              </div>
            )}
          </div>
        );
      case 'townhouse':
        return (
          <div>
            <TownhouseDetails property={property as TownhouseDTO} seller={seller} agent={agent} />
            {user?.role === "ADMIN" && (
              <div className="edit-button-container">
                <button className="edit-button" onClick={handleEditClick}>Editar Imóvel</button>
              </div>
            )}
          </div>
        );
      case 'house':
        return (
          <div>
            <HouseDetails property={property as HouseDTO} seller={seller} agent={agent} />
            {user?.role === "ADMIN" && (
              <div className="edit-button-container">
                <button className="edit-button" onClick={handleEditClick}>Editar Imóvel</button>
              </div>
            )}
          </div>
        );
      case 'commercialbuilding':
        return (
          <div>
            {user?.role === "ADMIN" && (
              <div className="edit-button-container">
                <button className="edit-button" onClick={handleEditClick}>Editar Imóvel</button>
              </div>
            )}
          </div>
        );
      case 'warehouse':
        return (
          <div>
            <WarehouseDetails property={property as WarehouseDTO} seller={seller} agent={agent} />
           {user?.role === "ADMIN" && (
             <div className="edit-button-container">
               <button className="edit-button" onClick={handleEditClick}>Editar Imóvel</button>
             </div>
           )}
         </div>
        );
      case 'commercialarea':
        return (
          <div>
            <CommercialAreaDetails property={property as CommercialAreaDTO} seller={seller} agent={agent} />
           {user?.role === "ADMIN" && (
             <div className="edit-button-container">
               <button className="edit-button" onClick={handleEditClick}>Editar Imóvel</button>
             </div>
           )}
         </div>
       );
      case 'farm':
        return (
          <div>
            <FarmDetails property={property as FarmDTO} seller={seller} agent={agent} />
            {user?.role === "ADMIN" && (
              <div className="edit-button-container">
                <button className="edit-button" onClick={handleEditClick}>Editar Imóvel</button>
              </div>
            )}
          </div>
        );
      case 'countryhouse':
        return (
          <div>
            <CountryHouseDetails property={property as CountryHouseDTO} seller={seller} agent={agent} />
           {user?.role === "ADMIN" && (
             <div className="edit-button-container">
               <button className="edit-button" onClick={handleEditClick}>Editar Imóvel</button>
             </div>
           )}
         </div>
       );
      case 'urbanland':
        return (
          <div>
            <UrbanLandDetails property={property as UrbanLandDTO} seller={seller} agent={agent} />
           {user?.role === "ADMIN" && (
             <div className="edit-button-container">
               <button className="edit-button" onClick={handleEditClick}>Editar Imóvel</button>
             </div>
           )}
         </div>
       );
      case 'commercialroom':
        return (
          <div>
            <CommercialRoomDetails property={property as CommercialRoomDTO} seller={seller} agent={agent} />
           {user?.role === "ADMIN" && (
             <div className="edit-button-container">
               <button className="edit-button" onClick={handleEditClick}>Editar Imóvel</button>
             </div>
           )}
         </div>
       );
      default:
        return <div>Tipo de imóvel não suportado</div>;
    }
  };

  return renderDetails();
};

export default PropertyDetails;
