import React from 'react';
import { TownhouseDTO, Seller, Agent,PropertyStatusDescription} from '../../types/models';
import { formatCPF, formatPhoneNumber, formatAddress } from "../../components/shared/shared";

interface Props {
  property: TownhouseDTO;
  seller: Seller;
  agent: Agent;
}

export const TownhouseDetails: React.FC<Props> = ({ property, seller, agent }) => (
  <div className="property-details-container">
      <div>
          <h1><strong> Sobrado - Código: </strong> {property.propertyCode}</h1>
          <div className="property-details-content">
          <h2>Informações</h2>
          <div className="two-column">
          <div>
            <p><strong>Nome do Condomínio:</strong> {property.nameOfGatedCommunity}</p>
            <p><strong>Endereço: </strong>{formatAddress(property)}</p>
            <p><strong>Preço:</strong> {property.price?.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' }) || 'Preço não disponível'}</p>
            <p><strong>Taxa de condomínio:</strong> {property.condominiumFee.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })}</p>
            {property.isRented && (<p><strong>Valor do aluguel:</strong>{" "}{property.rentalValue?.toLocaleString("pt-BR", { style: "currency", currency: "BRL" })}</p>)}
            {property.orientation && <p><strong>Orientação:</strong> {property.orientation}</p>}
         </div>
          <div>
           <p><strong>Ocupado:</strong> {property.isInhabited ? 'Sim' : 'Não'}</p>
           <p><strong>Alugado:</strong> {property.isRented ? 'Sim' : 'Não'}</p>
          <p><strong>Área de terreno:</strong> {property.totalArea?.toLocaleString()} m²</p>
          <p><strong>Área construída:</strong> {property.usableArea?.toLocaleString()} m²</p>
        </div>
     </div>
    </div>
    </div>

    <div className="property-details-content">
      <div className="three-column">
        <div className="property-details-content">
          <h2>Divisões internas</h2>
            <p><strong>Quartos:</strong> {property.bedrooms}</p>
            <p><strong>Suítes:</strong> {property.suites}</p>
            <p><strong>Salas:</strong> {property.livingRoom}</p>
            <p><strong>Banheiro social:</strong> {property.bathrooms}</p>
            <p><strong>Lavabo:</strong> {property.toilet}</p>
            <p><strong>Escritórios:</strong> {property.offices}</p>
            <p><strong>Cozinha:</strong> {property.hasKitchen ? 'Sim' : 'Não'}</p>
            <p><strong>Despensa:</strong> {property.hasPantry ? 'Sim' : 'Não'}</p>
            <p><strong>Lavanderia:</strong> {property.hasLaundry ? 'Sim' : 'Não'}</p>
            <p><strong>Quarto para funcionário:</strong> {property.hasEmployeeRoom ? 'Sim' : 'Não'}</p>
            <p><strong>Banheiro para funcionário:</strong> {property.hasEmployeeBathroom ? 'Sim' : 'Não'}</p>
        </div>
        <div className="property-details-content">
         <h2>Infraestrutura</h2>
          <p><strong>Piso:</strong> {property.floorType}</p>
          <p><strong>Vagas de Garagem:</strong> {property.garageSpaces}</p>
          <p><strong>Garagens em gaveta:</strong> {property.hasGaragesInRow ? 'Sim' : 'Não'}</p>
          <p><strong>Armários:</strong> {property.hasCabinets ? 'Sim' : 'Não'}</p>
          <p><strong>Interfone:</strong> {property.hasIntercom ? 'Sim' : 'Não'}</p>
          <p><strong>Câmeras de Vigilância:</strong> {property.hasSurveillanceCameras ? 'Sim' : 'Não'}</p>
          <p><strong>Varanda Gourmet:</strong> {property.hasGourmetBalcony ? 'Sim' : 'Não'}</p>
          <p><strong>Ar-condicionado:</strong> {property.hasAirConditioning ? 'Sim' : 'Não'}</p>
        </div>
        <div className="property-details-content">
          <h2>Comodidades</h2>
          <p><strong>Churrasqueira:</strong> {property.hasBarbecueGrill ? 'Sim' : 'Não'}</p>
          <p><strong>Piscina:</strong> {property.hasSwimmingPool ? 'Sim' : 'Não'}</p>
          <p><strong>Sauna:</strong> {property.hasSauna ? 'Sim' : 'Não'}</p>
          <p><strong>Portão Eletrônico:</strong> {property.hasElectronicGate ? 'Sim' : 'Não'}</p>
          <p><strong>Portaria Eletrônica:</strong> {property.hasElectronicDoorman ? 'Sim' : 'Não'}</p>
          </div>
         </div>
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
        {/* Descrição */}
        <div className="property-details-content">
          <h2>Descrição</h2>
          <textarea
            value={property.description || ''}
            readOnly
            rows={5}
          />
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
