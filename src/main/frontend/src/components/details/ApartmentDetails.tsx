import React from 'react';
import { ApartmentDTO, Seller, Agent } from '../../types/models';
import { formatPhoneNumber, formatAddress, formatArea } from "../../components/shared/shared";

interface Props {
  property: ApartmentDTO;
  seller: Seller;
  agent: Agent;
}

const ApartmentDetails: React.FC<Props> = ({ property, seller, agent }) => (
  <div className="max-w-[210mm] mx-auto p-4 font-sans text-sm leading-tight text-gray-900 print:p-2 print:text-xs print:leading-snug">
    <h1 className="text-lg font-bold mb-2">Apartamento - Código: {property.propertyCode}</h1>

    <section className="mb-3">
      <h2 className="font-semibold mb-1 border-b border-gray-300 pb-1">Informações</h2>
      <div className="grid grid-cols-2 gap-x-6 gap-y-1">
        <div className="grid">
          <p><strong className="text-gray-700">Nome do Prédio:</strong> {property.nameOfBuilding}</p>
          <p><strong className="text-gray-700">Número do Apartamento:</strong> {property.apartmentNumber}</p>
          <p><strong className="text-gray-700">Endereço:</strong> {formatAddress(property)}</p>
          <p><strong className="text-gray-700">Preço:</strong> {property.price?.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' }) || 'N/D'}</p>
          <p><strong className="text-gray-700">Taxa de Condomínio:</strong> {property.condominiumFee.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })}</p>
          {property.isRented && <p><strong className="text-gray-700">Valor do Aluguel:</strong> {property.rentalValue?.toLocaleString("pt-BR", { style: "currency", currency: "BRL" })}</p>}
        </div>
        <div className="flex flex-col">
          <p><strong className="text-gray-700">Orientação:</strong> {property.orientation || 'N/D'}</p>
          <p><strong className="text-gray-700">Ocupado:</strong> {property.isInhabited ? 'Sim' : 'Não'}</p>
          <p><strong className="text-gray-700">Alugado:</strong> {property.isRented ? 'Sim' : 'Não'}</p>
          {property.totalArea && <p><strong className="text-gray-700">Área Total:</strong> {formatArea(property.totalArea)} m²</p>}
          {property.privateArea && <p><strong className="text-gray-700">Área Privativa:</strong> {formatArea(property.privateArea)} m²</p>}
          <p><strong className="text-gray-700">Área Útil:</strong> {formatArea(property.usableArea)} m²</p>
        </div>
      </div>
    </section>

    <section className="mb-3">
      <h2 className="font-semibold mb-1 border-b border-gray-300 pb-1">Divisões Internas</h2>
      <div className="grid grid-cols-3 gap-x-4 gap-y-1">
        <p><strong className="text-gray-700">Quartos:</strong> {property.bedrooms}</p>
        <p><strong className="text-gray-700">Suítes:</strong> {property.suites}</p>
        <p><strong className="text-gray-700">Salas:</strong> {property.livingRoom}</p>
        <p><strong className="text-gray-700">Banheiro Social:</strong> {property.bathrooms}</p>
        <p><strong className="text-gray-700">Lavabo:</strong> {property.toilet}</p>
        <p><strong className="text-gray-700">Escritórios:</strong> {property.offices}</p>
        <p><strong className="text-gray-700">Cozinha:</strong> {property.hasKitchen ? 'Sim' : 'Não'}</p>
        <p><strong className="text-gray-700">Despensa:</strong> {property.hasPantry ? 'Sim' : 'Não'}</p>
        <p><strong className="text-gray-700">Sacada:</strong> {property.hasBalcony ? 'Sim' : 'Não'}</p>
        <p><strong className="text-gray-700">Varanda Gourmet:</strong> {property.hasGourmetBalcony ? 'Sim' : 'Não'}</p>
        <p><strong className="text-gray-700">Lavanderia:</strong> {property.hasLaundry ? 'Sim' : 'Não'}</p>
        <p><strong className="text-gray-700">Quarto para funcionário:</strong> {property.hasEmployeeRoom ? 'Sim' : 'Não'}</p>
        <p><strong className="text-gray-700">Banheiro para funcionário:</strong> {property.hasEmployeeBathroom ? 'Sim' : 'Não'}</p>
      </div>
    </section>

    <section className="mb-3">
      <h2 className="font-semibold mb-1 border-b border-gray-300 pb-1">Comodidades</h2>
      <div className="grid grid-cols-3 gap-x-4 gap-y-1">
        <p><strong className="text-gray-700">Piso:</strong> {property.floorType}</p>
        <p><strong className="text-gray-700">Vagas de Garagem:</strong> {property.garageSpaces}</p>
        <p><strong className="text-gray-700">Garagens em gaveta:</strong> {property.hasGaragesInRow ? 'Sim' : 'Não'}</p>
        <p><strong className="text-gray-700">Escaninho:</strong> {property.hasCompartment ? 'Sim' : 'Não'}</p>
        <p><strong className="text-gray-700">Armários:</strong> {property.hasCabinets ? 'Sim' : 'Não'}</p>
        <p><strong className="text-gray-700">Interfone:</strong> {property.hasIntercom ? 'Sim' : 'Não'}</p>
        <p><strong className="text-gray-700">Câmeras de Vigilância:</strong> {property.hasSurveillanceCameras ? 'Sim' : 'Não'}</p>
        <p><strong className="text-gray-700">Rede de segurança nas janelas:</strong> {property.hasBars ? 'Sim' : 'Não'}</p>
        <p><strong className="text-gray-700">Ar-condicionado:</strong> {property.hasAirConditioning ? 'Sim' : 'Não'}</p>
        <p><strong className="text-gray-700">Sauna Exclusiva:</strong> {property.hasExclusiveSauna ? 'Sim' : 'Não'}</p>
        <p><strong className="text-gray-700">Piscina Exclusiva:</strong> {property.hasPrivateSwimmingPool ? 'Sim' : 'Não'}</p>
      </div>
    </section>

    <section className="mb-3">
      <h2 className="font-semibold mb-1 border-b border-gray-300 pb-1">Infraestrutura do Condomínio</h2>
      <div className="grid grid-cols-2 gap-x-6 gap-y-1">
        <div className="flex flex-col">
          <p><strong className="text-gray-700">Salão de Festas:</strong> {property.hasPartyHall ? 'Sim' : 'Não'}</p>
          <p><strong className="text-gray-700">Salão de Jogos:</strong> {property.hasGameRoom ? 'Sim' : 'Não'}</p>
          <p><strong className="text-gray-700">Playground:</strong> {property.hasPlayground ? 'Sim' : 'Não'}</p>
          <p><strong className="text-gray-700">Brinquedoteca:</strong> {property.hasToyArea ? 'Sim' : 'Não'}</p>
          <p><strong className="text-gray-700">Quadra de Esportes:</strong> {property.hasSportsCourt ? 'Sim' : 'Não'}</p>
          <p><strong className="text-gray-700">Piscina:</strong> {property.hasSwimmingPool ? 'Sim' : 'Não'}</p>
          <p><strong className="text-gray-700">Academia:</strong> {property.hasGym ? 'Sim' : 'Não'}</p>
          <p><strong className="text-gray-700">Sauna:</strong> {property.hasSauna ? 'Sim' : 'Não'}</p>
          <p><strong className="text-gray-700">Churrasqueira:</strong> {property.hasBarbecue ? 'Sim' : 'Não'}</p>
        </div>
        <div className="flex flex-col">
          <p><strong className="text-gray-700">Portão Eletrônico:</strong> {property.hasElectronicGate ? 'Sim' : 'Não'}</p>
          <p><strong className="text-gray-700">Portaria Eletrônica:</strong> {property.hasElectronicDoorman ? 'Sim' : 'Não'}</p>
          <p><strong className="text-gray-700">Anos de construção:</strong> {property.yearsOfConstruction}</p>
          <p><strong className="text-gray-700">Mezanino:</strong> {property.hasMezzanine ? 'Sim' : 'Não'}</p>
          <p><strong className="text-gray-700">Elevador:</strong> {property.elevator}</p>
          <p><strong className="text-gray-700">Total de Apartamentos:</strong> {property.totalOfApartments}</p>
          <p><strong className="text-gray-700">Total de Andares:</strong> {property.numberOfFloors}</p>
        </div>
      </div>
    </section>

    <section className="mb-3">
      <h2 className="font-semibold mb-1 border-b border-gray-300 pb-1">Dados Adicionais</h2>
      <div className="grid grid-cols-5 gap-x-4 gap-y-1">
        <p><strong className="text-gray-700">Local das Chaves:</strong> {property.placeOfKeys || 'N/D'}</p>
        <p><strong className="text-gray-700">Hora de Visita:</strong> {property.visitingTime || 'N/D'}</p>
        <p><strong className="text-gray-700">Data de cadastro:</strong> {new Date(property.createdAt).toLocaleDateString()} </p>
        <p><strong className="text-gray-700">Última Atualização:</strong> {new Date(property.updatedAt).toLocaleDateString()} </p>
        <p><strong className="text-gray-700">Proprietário:</strong> {seller.name} </p>
      </div>
    </section>

    <section className="mb-3">
      <h2 className="font-semibold mb-1 border-b border-gray-300 pb-1">Descrição</h2>
      <p className="whitespace-pre-wrap">{property.description || 'Sem descrição disponível.'}</p>
    </section>

    <section>
      <h2 className="font-semibold mb-1 border-b border-gray-300 pb-1">Proprietário</h2>
      <div className="flex flex-col">
        <p><strong className="text-gray-700">Nome:</strong> {seller.name}</p>
        <p><strong className="text-gray-700">Telefone:</strong> {formatPhoneNumber(seller.phone)}</p>
        <p><strong className="text-gray-700">Email:</strong> {seller.email}</p>
      </div>
    </section>
  </div >
);

export default ApartmentDetails;
