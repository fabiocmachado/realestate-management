import React from "react";
import { UrbanLandDTO, Agent, Seller, PropertyStatusDescription} from '../../types/models';
import  { formatCPF, formatPhoneNumber,formatAddress, formatArea} from "../../components/shared/shared"

interface Props {
 property: UrbanLandDTO;
 seller: Seller;
 agent: Agent;
}

export const UrbanLandDetails: React.FC<Props> = ({ property, seller, agent }) => (
   <div className="property-details-container">
       <h1><strong> Terreno - Código: </strong> {property.propertyCode}</h1>
       <div className="property-details-content">
       <h2>Informações</h2>
       <div className="four-column">
       <div>
        <p><strong>Endereço:</strong>{formatAddress(property)}</p>
       </div>
       <div>
         <p><strong>Preço:</strong> {property.price?.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' }) || 'Preço não disponível'}</p>
       </div>
       <div>
          {property.orientation && <p><strong>Orientação:</strong> {property.orientation}</p>}
        </div>
       <div>
       <p><strong>Tamanho:</strong> {formatArea(property.totalArea)} m²</p>
        </div>
     </div>
     </div>
      <div className="two-column">
     <div className="property-details-content">
       <h2>Infraestrutura</h2>
       <p><strong>Muro:</strong> {property.hasWall ? 'Sim' : 'Não'}</p>
       <p><strong>Asfalto:</strong> {property.hasAsphalt ? 'Sim' : 'Não'}</p>
      </div>
       <div className="property-details-content">
       <h2>Dados Adicionais</h2>
         <div className="one-column">
         {property.placeOfKeys && <p><strong>Local das Chaves:</strong><br></br> {property.placeOfKeys}</p>}
         <p><strong>Hora de Visita:</strong><br></br> {property.visitingTime}</p>
         {property.createdAt && <p><strong>Data de cadastro:</strong><br></br> {new Date(property.createdAt).toLocaleDateString()}</p>}
         {property.updatedAt && <p><strong>Última Atualização:</strong><br></br> {new Date(property.updatedAt).toLocaleDateString()}</p>}
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
