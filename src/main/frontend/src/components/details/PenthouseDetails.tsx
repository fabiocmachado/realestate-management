import React from 'react';
import { PenthouseDTO, Seller, Agent,PropertyStatusDescription,PropertyCategoryDescription } from '../../types/models';
import { formatCPF, formatPhoneNumber, formatAddress } from "../../components/shared/shared";

interface Props {
  property: PenthouseDTO;
  seller: Seller;
  agent: Agent;
}

export const PenthouseDetails: React.FC<Props> = ({ property, seller, agent }) => (
  <div className="property-details-container">
      <div>
        <h3><strong> Cobertura - Código: </strong> {property.propertyCode}</h3>
       <div className="two-column">
       <div className="property-details-content">
        <p><strong>Nome do Prédio:</strong> {property.nameOfBuilding}</p>
        <p><strong>Número do apartamento:</strong> {property.apartmentNumber}</p>
        <p><strong>Endereço: </strong>{formatAddress(property)}</p>
         <p><strong>Preço:</strong> {property.price?.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' }) || 'Preço não disponível'}</p>
         <p><strong>Taxa de condomínio:</strong> {property.condominiumFee.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })}</p>
         {property.isRented && (<p><strong>Valor do aluguel:</strong>{" "}{property.rentalValue?.toLocaleString("pt-BR", { style: "currency", currency: "BRL" })}</p>)}
        </div>
        <div className="property-details-content">
         {property.orientation && <p><strong>Orientação:</strong> {property.orientation}</p>}
          <p><strong>Ocupado:</strong> {property.isInhabited ? 'Sim' : 'Não'}</p>
          <p><strong>Alugado:</strong> {property.isRented ? 'Sim' : 'Não'}</p>
          <p><strong>Área Total:</strong> {property.totalArea?.toLocaleString()} m²</p>
          <p><strong>Área Privativa:</strong> {property.privateArea?.toLocaleString()} m²</p>
          <p><strong>Área Útil:</strong> {property.usableArea?.toLocaleString()} m²</p>
      </div>
      </div>
    </div>

<div className="two-column">
    <div className="property-details-content">
      <h2>Divisões Internas</h2>
      <div className="one-column">
        <div>
          <p><strong>Quartos:</strong> {property.bedrooms}</p>
          <p><strong>Suítes:</strong> {property.suites}</p>
          <p><strong>Salas:</strong> {property.livingRoom}</p>
          <p><strong>Banheiro social:</strong> {property.bathrooms}</p>
          <p><strong>Lavabo:</strong> {property.toilet}</p>
          <p><strong>Escritórios:</strong> {property.offices}</p>
          <p><strong>Cozinha:</strong> {property.hasKitchen ? 'Sim' : 'Não'}</p>
          <p><strong>Despensa:</strong> {property.hasPantry ? 'Sim' : 'Não'}</p>
          <p><strong>Sacada:</strong> {property.hasBalcony ? 'Sim' : 'Não'}</p>
          <p><strong>Varanda Gourmet:</strong> {property.hasGourmetBalcony ? 'Sim' : 'Não'}</p>
          <p><strong>Lavanderia:</strong> {property.hasLaundry ? 'Sim' : 'Não'}</p>
          <p><strong>Quarto para funcionário:</strong> {property.hasEmployeeRoom ? 'Sim' : 'Não'}</p>
          <p><strong>Banheiro para funcionário:</strong> {property.hasEmployeeBathroom ? 'Sim' : 'Não'}</p>
          </div>
      </div>
    </div>

    <div className="property-details-content">
      <h2>Comodidades</h2>
      <div className="one-column">
        <div>
          <p><strong>Piso:</strong> {property.floorType}</p>
          <p><strong>Vagas de Garagem:</strong> {property.garageSpaces}</p>
          <p><strong>Garagens em gaveta:</strong> {property.hasGaragesInRow ? 'Sim' : 'Não'}</p>
          <p><strong>Escaninho:</strong> {property.hasCompartment ? 'Sim' : 'Não'}</p>
          <p><strong>Armários:</strong> {property.hasCabinets ? 'Sim' : 'Não'}</p>
          <p><strong>Interfone:</strong> {property.hasIntercom ? 'Sim' : 'Não'}</p>
          <p><strong>Câmeras de Vigilância:</strong> {property.hasSurveillanceCameras ? 'Sim' : 'Não'}</p>
          <p><strong>Rede de segurança nas janelas:</strong> {property.hasBars ? 'Sim' : 'Não'}</p>
          <p><strong>Ar-condicionado:</strong> {property.hasAirConditioning ? 'Sim' : 'Não'}</p>
          <p><strong>Sauna Exclusiva:</strong> {property.hasExclusiveSauna ? 'Sim' : 'Não'}</p>
          <p><strong>Piscina Exclusiva:</strong> {property.hasPrivateSwimmingPool ? 'Sim' : 'Não'}</p>
        </div>
      </div>
    </div>
  </div>
  <div className="property-details-content">
        <h2>Infraestrutrua do Condomínio</h2>
        <div className="two-column">
          <div>
            <p><strong>Salão de Festas:</strong> {property.hasPartyHall ? 'Sim' : 'Não'}</p>
            <p><strong>Salão de Jogos:</strong> {property.hasGameRoom ? 'Sim' : 'Não'}</p>
            <p><strong>Playground:</strong> {property.hasPlayground ? 'Sim' : 'Não'}</p>
            <p><strong>Brinquedoteca:</strong> {property.hasToyArea ? 'Sim' : 'Não'}</p>
            <p><strong>Quadra de Esportes:</strong> {property.hasSportsCourt ? 'Sim' : 'Não'}</p>
            <p><strong>Piscina:</strong> {property.hasSwimmingPool ? 'Sim' : 'Não'}</p>
            <p><strong>Academia:</strong> {property.hasGym ? 'Sim' : 'Não'}</p>
            <p><strong>Sauna:</strong> {property.hasSauna ? 'Sim' : 'Não'}</p>
            <p><strong>Churrasqueira:</strong> {property.hasBarbecue ? 'Sim' : 'Não'}</p>
          </div>
          <div>
            <p><strong>Portão Eletrônico:</strong> {property.hasElectronicGate ? 'Sim' : 'Não'}</p>
            <p><strong>Portaria Eletrônica:</strong> {property.hasElectronicDoorman ? 'Sim' : 'Não'}</p>
            <p><strong>Anos de construção:</strong> {property.yearsOfConstruction}</p>
            <p><strong>Mezanino:</strong> {property.hasMezzanine ? 'Sim' : 'Não'}</p>
            <p><strong>Elevador:</strong> {property.elevator}</p>
            <p><strong>Blocos:</strong> {property.numberOfBlocks}</p>
            <p><strong>Total de Apartamentos:</strong> {property.totalOfApartments}</p>
            <p><strong>Total de Andares:</strong> {property.numberOfFloors}</p>
          </div>
        </div>
      </div>
    <div className="property-details-content">
      <h2>Outras Informações</h2>
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


export default PenthouseDetails;
