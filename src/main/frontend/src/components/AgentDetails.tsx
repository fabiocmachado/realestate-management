import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { Agent, PropertyDTO, RoleLabels } from "../types/models";
import { getAgentById } from "../services/agentService";
import { getProperties } from "../services/propertyService";
import { formatCPF, formatPhoneNumber } from "../components/shared/shared";
import '../styles/agentDetails.css'


const formatAddress = (agent: Agent) => {
  const addressParts = [
    agent.street,
    agent.block,
    agent.lot,
    agent.number,
    agent.complement,
    agent.neighborhood,
    agent.city,
    agent.state,
  ];
  return addressParts.filter(Boolean).join(", ");
};

interface AgentDetailsProps {}

const AgentDetails: React.FC<AgentDetailsProps> = () => {
  const [agent, setAgent] = useState<Agent | null>(null);
  const { id } = useParams<{ id: string }>();
  const [properties, setProperties] = useState<PropertyDTO[]>([]);
  const navigate = useNavigate();
  const [error, setError] = useState<string | null>(null);
  const [loadingProperties, setLoadingProperties] = useState<boolean>(false);

  useEffect(() => {
    if (id) {
      const numericId = parseInt(id, 10);
      if (!isNaN(numericId)) {
        getAgentById(numericId)
          .then(async (data) => {
            setAgent(data);
            setLoadingProperties(true);
            try {
              const response = await getProperties({ page: 0, size: 1000 }, undefined, undefined);
              const fetchedProperties = response?.content?.filter(prop =>
                prop.id !== undefined && data.prospectedProperties.includes(prop.id)
              );
              setProperties(fetchedProperties as PropertyDTO[]);
            } catch (err) {
              console.error("Erro ao carregar detalhes das propriedades prospectadas:", err);
              setError("Erro ao carregar detalhes das propriedades prospectadas. Por favor, tente novamente.");
            } finally {
              setLoadingProperties(false);
            }
          })
          .catch((error) => {
            console.error("Erro ao carregar os detalhes do agente", error);
            setError("Erro ao carregar os detalhes do agente. Por favor, tente novamente.");
          });
      } else {
        setError("ID inválido");
      }
    } else {
      setError("ID não fornecido");
    }
  }, [id]);

  if (error) {
    return <div className="error-message">{error}</div>;
  }

  if (!agent || loadingProperties) {
    return <div className="loading-message">Carregando...</div>;
  }

  const handleEdit = (id: number) => {
        navigate(`/edit-agent/${id}`);
      };

  return (
    <div className="agent-details-container">
      <div className="agent-details-header">
        <h2>Detalhes do Agente</h2>
      </div>
      <div className="agent-details-info">
        <p><strong>Nome:</strong> {agent.name}</p>
        <p><strong>Email:</strong> {agent.email}</p>
        <p><strong>Telefone:</strong> {formatPhoneNumber(agent.phone)}</p>
        <p><strong>CPF:</strong> {formatCPF(agent.cpf)}</p>
        <p><strong>RG:</strong> {agent.rg}</p>
        <p><strong>CRECI:</strong> {agent.licenseNumber}</p>
        <p><strong>Endereço:</strong> {formatAddress(agent)}</p>
        <p><strong>Função:</strong> {RoleLabels[agent.role]}</p>
      </div>
      <div><button onClick={() => handleEdit(agent.id!)}>Editar</button></div>
      <div className="agent-details-properties">
        <p><strong>Imóveis captados:</strong></p>
        <ul>
          {properties.map((property) => (
            <li key={property.id}><strong>Código do imóvel:</strong> {property.propertyCode}</li>
          ))}
        </ul>
      </div>
    </div>
  );
};

export default AgentDetails;
