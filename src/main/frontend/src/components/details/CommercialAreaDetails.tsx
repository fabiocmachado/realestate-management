import React from 'react';
import { CommercialAreaDTO, Seller, Agent, PropertyStatusDescription } from '../../types/models';
import { formatCPF, formatPhoneNumber, formatAddress } from "../../components/shared/shared";

interface Props {
  property: CommercialAreaDTO;
  seller: Seller;
  agent: Agent;
}

export const CommercialAreaDetails: React.FC<Props> = ({ property, seller, agent }) => (
  <div className="property-details-container">
    <h1><strong> Área comercial - Código: </strong> {property.propertyCode}</h1>
    <div className="three-column">
    <div className="property-details-content">
        <p><strong>Endereço: </strong>{formatAddress(property)}</p>
      </div>
    <div className="property-details-content">
       <p><strong>Preço:</strong> {property.price?.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' }) || 'Preço não disponível'}</p>
       <p><strong>Taxa de condomínio:</strong> {property.condominiumFee.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })}</p>
       {property.isRented && (<p><strong>Valor do aluguel:</strong>{" "}{property.rentalValue?.toLocaleString("pt-BR", { style: "currency", currency: "BRL" })}</p>)}
      </div>
    <div className="property-details-content">
        {property.orientation && <p><strong>Orientação:</strong> {property.orientation}</p>}
         <p><strong>Área terreno:</strong> {property.totalArea?.toLocaleString()} m²</p>
        <p><strong>Alugado:</strong> {property.isRented ? 'Sim' : 'Não'}</p>
      </div>
      </div>
    <div className="two-column">
    <div className="property-details-content">
      <h2>Características da Área Comercial</h2>
      <p><strong>Possui Muro:</strong> {property.hasWall ? 'Sim' : 'Não'}</p>
      <p><strong>Possui Asfalto:</strong> {property.hasAsphalt ? 'Sim' : 'Não'}</p>
     </div>
      <div className="property-details-content">
       <h2>Outras Informações</h2>
         <div className="one-column">
         <div>
         {property.placeOfKeys && <p><strong>Local das Chaves:</strong>{property.placeOfKeys}</p>}
         <p><strong>Hora de Visita:</strong>{property.visitingTime}</p>
         {property.createdAt && <p><strong>Data de cadastro:</strong> {new Date(property.createdAt).toLocaleDateString()}</p>}
         {property.updatedAt && <p><strong>Última Atualização:</strong> {new Date(property.updatedAt).toLocaleDateString()}</p>}
         {PropertyStatusDescription[property.status] && <p><strong>Status:</strong> {PropertyStatusDescription[property.status]}</p>}
           <p><strong>Captador do Imóvel:</strong> {agent.name}</p>
        </div>
        </div>
        </div>
    </div>
    <div className="property-details-content">
      <h2>Descrição</h2>
      <textarea value={property.description || ''} readOnly rows={5} />
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
