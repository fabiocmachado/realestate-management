import React from 'react';
import { WarehouseDTO, Seller, Agent } from '../../types/models';
import { formatCPF, formatPhoneNumber,formatAddress } from "../../components/shared/shared";

interface Props {
  property: WarehouseDTO;
  seller: Seller;
  agent: Agent;
}

export const WarehouseDetails: React.FC<Props> = ({ property, seller, agent }) => (
  <div className="property-details-container">
      <h1><strong> Galpão - Código: </strong> {property.propertyCode}</h1>
       <div className="four-column">
       <div className="property-details-content">
        <p><strong>Endereço:</strong>{formatAddress(property)}</p>
      </div>
       <div className="property-details-content">
      <p><strong>Preço:</strong> {property.price?.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' }) || 'Preço não disponível'}</p>
      <p><strong>Taxa de Condomínio:</strong> {property.condominiumFee?.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })}</p>
    </div>
      <div className="property-details-content">
      {property.orientation && <p><strong>Orientação:</strong> {property.orientation}</p>}
      <p><strong>Ocupado:</strong> {property.isInhabited ? 'Sim' : 'Não'}</p>
      <p><strong>Alugado:</strong> {property.isRented ? 'Sim' : 'Não'}</p>
    </div>
  <div className="property-details-content">
      <p><strong>Área de terreno:</strong> {property.totalArea?.toLocaleString()} m²</p>
      <p><strong>Área Útil:</strong> {property.usableArea?.toLocaleString()} m²</p>
    </div>
    </div>
    <div className="two-column">
    <div className="property-details-content">
      <h2>Características da Área Comercial</h2>
      <p><strong>Está Alugada:</strong> {property.isRented ? 'Sim' : 'Não'}</p>
      <p><strong>Valor do Aluguel:</strong> {property.rentalValue
        ? property.rentalValue.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })
        : 'N/A'}</p>
      <p><strong>Possui Ar-condicionado:</strong> {property.hasAirConditioning ? 'Sim' : 'Não'}</p>
      <p><strong>Possui Mezanino:</strong> {property.hasMezzanine ? 'Sim' : 'Não'}</p>
      <p><strong>Possui Cozinha:</strong> {property.hasKitchen ? 'Sim' : 'Não'}</p>
      <p><strong>Vagas de Garagem:</strong> {property.garageSpaces}</p>
      <p><strong>Garagens em Linha:</strong> {property.hasGaragesInRow ? 'Sim' : 'Não'}</p>
      <p><strong>Possui Energia Solar:</strong> {property.hasSolarEnergy ? 'Sim' : 'Não'}</p>
    </div>

    <div className="property-details-content">
           <h2>Outras Informações</h2>
             <div className="one-column">
             <div>
             {property.placeOfKeys && <p><strong>Local das Chaves:</strong>{property.placeOfKeys}</p>}
             <p><strong>Hora de Visita:</strong>{property.visitingTime}</p>
             {property.createdAt && <p><strong>Data de cadastro:</strong> {new Date(property.createdAt).toLocaleDateString()}</p>}
             {property.updatedAt && <p><strong>Última Atualização:</strong> {new Date(property.updatedAt).toLocaleDateString()}</p>}
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

export default WarehouseDetails;