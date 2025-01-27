import React from 'react';
import { CountryHouseDTO, Agent, Seller } from '../../types/models';
import { formatCPF, formatPhoneNumber, formatAddress } from "../../components/shared/shared";

interface Props {
  property: CountryHouseDTO;
  seller: Seller;
  agent: Agent;
}

const convertToHectares = (areaInAlqueires?: number): string => {
  if (areaInAlqueires == null) return 'N/A';
  const hectares = areaInAlqueires * 4.84;
  return `${areaInAlqueires} alq (${hectares.toFixed(2)} ha)`;
};

export const CountryHouseDetails: React.FC<Props> = ({ property, seller, agent }) => (
  <div className="property-details-container">
    <div>
      <h1><strong> Chácara - Código: </strong> {property.propertyCode}</h1>
     <div className="property-details-content">
       <h2>Informações</h2>
        <div className="three-column">
        <div>
          <p><strong>Nome:</strong> {property.name}</p>
          <p><strong>Endereço:</strong> {formatAddress(property)}</p>
        </div>
        <div>
          <p><strong>Preço:</strong> {property.price?.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' }) || 'Preço não disponível'}</p>
          <p><strong>Arrendada:</strong> {property.isRented ? 'Sim' : 'Não'}</p>
           {property.isRented && (<p><strong>Valor do arrendamento:</strong> {property.rentalValue?.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' }) || 'N/A'}</p>)}
        </div>
        <div>
          <p><strong>Área Total:</strong> {convertToHectares(property.totalAreaRural)}</p>
          <p><strong>Área Formada:</strong> {convertToHectares(property.formedArea)}</p>
          <p><strong>Reserva Legal:</strong> {convertToHectares(property.legalReserveArea)}</p>
        </div>
      </div>
     </div>
    </div>

    <div className="two-column">
      <div className="property-details-content">
        <h2>Infraestrutura</h2>
        <p><strong>Casa Principal:</strong> {property.mainHouse || 'Sem informações'}</p>
        <p><strong>Casa para Funcionários:</strong> {property.staffHouse || 'Sem informações'}</p>
        <p><strong>Galpão:</strong> {property.warehouse || 'Sem informações'}</p>
        <p><strong>Alojamento:</strong> {property.accommodation || 'Sem informações'}</p>
        <p><strong>Curral:</strong> {property.pens || 'Sem informações'}</p>
        <p><strong>Energia:</strong> {property.energy || 'Sem informações'}</p>
        <p><strong>Pista de Pouso:</strong> {property.hasLandingStrip ? 'Sim' : 'Não'}</p>
      </div>
      <div className="property-details-content">
        <h2>Pastagens e Conservação</h2>
        <p><strong>Capacidade de Rebanho:</strong> {property.herdSupport ? `${property.herdSupport} animais` : 'Sem informações'}</p>
        <p><strong>Quantidade de pastos:</strong> {property.pastures || 'Sem informações'}</p>
        <p><strong>Tipo de Solo:</strong> {property.typeOfSoil || 'Sem informações'}</p>
        <p><strong>Pastagem Predominante:</strong> {property.predominantPasture || 'Sem informações'}</p>
        <p><strong>Outras Pastagens:</strong> {property.otherPastures || 'Sem informações'}</p>
        <p><strong>Conservação das Pastagens:</strong> {property.pastureConservation || 'N/A'}</p>
        <p><strong>Cercas de Arame Liso:</strong> {property.hasSmoothWireFence ? 'Sim' : 'Não'}</p>
      </div>
    </div>

    <div className="two-column">
      <div className="property-details-content">
        <h2>Distâncias</h2>
        <p><strong>Distância até Goiânia:</strong> {property.distanceOfGyn || 'Sem informações'} km</p>
        <p><strong>Distância até a Cidade:</strong> {property.distanceOfCity || 'Sem informações'} km</p>
        <p><strong>Distância de Estrada de Terra:</strong> {property.distanceDirtRoad || 'Sem informações'} km</p>
      </div>
      <div className="property-details-content">
        <h2>Recursos Naturais</h2>
        <p><strong>Topografia:</strong> {property.topography || 'Sem informações'}</p>
        <p><strong>Rochas:</strong> {property.hasRocks ? 'Sim' : 'Não'}</p>
        <p><strong>Rios:</strong> {property.rivers || 'Sem informações'}</p>
        <p><strong>Represas:</strong> {property.dams || 'Sem informações'}</p>
        <p><strong>Pomar:</strong> {property.hasOrchard ? 'Sim' : 'Não'}</p>
      </div>
    </div>
    <div className="property-details-content">
        <h2>Descrição</h2>
        <textarea value={property.description || ''} readOnly rows={5} />
      </div>

      <div className="property-details-content">
       <h2>Dados Adicionais</h2>
          <div className="five-column">
           <div>
           {property.placeOfKeys && <p><strong>Local das Chaves:</strong><br></br> {property.placeOfKeys}</p>}
           </div>
           <div>
           <p><strong>Hora de Visita:</strong><br></br> {property.visitingTime}</p>
           </div>
           <div>
           {property.createdAt && <p><strong>Data de cadastro:</strong><br></br> {new Date(property.createdAt).toLocaleDateString()}</p>}
           </div>
           <div>
           {property.updatedAt && <p><strong>Última Atualização:</strong><br></br> {new Date(property.updatedAt).toLocaleDateString()}</p>}
           </div>
           <div>
           <p><strong>Captador do Imóvel:</strong><br></br> {agent.name}</p>
           </div>
         </div>
         </div>
    <div className="property-details-content">
      <h2>Proprietário</h2>
      <div className="three-column">
        <div>
          <p><strong>Nome:</strong> {seller.name}</p>
        </div>
        <div>
          <p><strong>Telefone:</strong> {formatPhoneNumber(seller.phone)}</p>
        </div>
        <div>
          <p><strong>Email:</strong> {seller.email}</p>
        </div>
      </div>
    </div>
  </div>
);
