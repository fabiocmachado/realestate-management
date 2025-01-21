import React from 'react';
import { CommercialRoomDTO, Seller, Agent, PropertyStatusDescription } from '../../types/models';
import { formatCPF, formatPhoneNumber,formatAddress } from "../../components/shared/shared";

interface Props {
  property: CommercialRoomDTO;
  seller: Seller;
  agent: Agent;
}

export const CommercialRoomDetails: React.FC<Props> = ({ property, seller, agent }) => (
  <div className="property-details-container">
  <div>
      <h3><strong> Sala comercial - Código: </strong> {property.propertyCode}</h3>
       <div className="four-column">
      <div className="property-details-content">
        <p><strong>Nome do Edifício:</strong> {property.nameOfBuilding}</p>
        <p><strong>Número da sala:</strong> {property.apartmentNumber}</p>
        <p><strong>Endereço:</strong>{formatAddress(property)}</p>
     </div>
      <div className="property-details-content">
        <p><strong>Preço:</strong> {property.price?.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' }) || 'Preço não disponível'}</p>
        <p><strong>Taxa de Condomínio:</strong> {property.condominiumFee?.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })}</p>
        {property.isRented && (<p><strong>Valor do aluguel:</strong>{" "}{property.rentalValue?.toLocaleString("pt-BR", { style: "currency", currency: "BRL" })}</p>)}
      </div>
      <div className="property-details-content">
        {property.orientation && <p><strong>Orientação:</strong> {property.orientation}</p>}
        <p><strong>Ocupado:</strong> {property.isInhabited ? 'Sim' : 'Não'}</p>
        <p><strong>Alugado:</strong> {property.isRented ? 'Sim' : 'Não'}</p>
      </div>
      <div className="property-details-content">
            <p><strong>Área Total:</strong> {property.totalArea?.toLocaleString()} m²</p>
            <p><strong>Área Privativa:</strong> {property.privateArea?.toLocaleString()} m²</p>
            <p><strong>Área Útil:</strong> {property.usableArea?.toLocaleString()} m²</p>
      </div>
     </div>
    </div>
     <div className="two-column">
      <div className="property-details-content">
        <h2>Características do Prédio Comercial</h2>
        <p><strong>Piso:</strong> {property.floorType}</p>
        <p><strong>Andares:</strong> {property.offices}</p>
        <p><strong>Tempo de Construção:</strong> {property.yearsOfConstruction} anos</p>
        <p><strong>Possui Câmeras de Vigilância:</strong> {property.hasSurveillanceCameras ? 'Sim' : 'Não'}</p>
        <p><strong>Possui Ar-condicionado:</strong> {property.hasAirConditioning ? 'Sim' : 'Não'}</p>
        <p><strong>Possui Mezanino:</strong> {property.hasMezzanine ? 'Sim' : 'Não'}</p>
        <p><strong>Possui Cozinha:</strong> {property.hasKitchen ? 'Sim' : 'Não'}</p>
        <p><strong>Vagas de Garagem:</strong> {property.garageSpaces}</p>
        <p><strong>Garagens em gaveta:</strong> {property.hasGaragesInRow ? 'Sim' : 'Não'}</p>
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


export default CommercialRoomDetails;
