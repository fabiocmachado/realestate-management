import React, { useEffect, useState, useCallback } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { Agent, PropertyDTO, RoleLabels } from "../types/models";
import { getAgentById } from "../services/agentService";
import { getProperties } from "../services/propertyService";
import {
  formatCPF,
  formatPhoneNumber,
  formatAgentAddress,
} from "../components/shared/shared";

const AgentDetails: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const [agent, setAgent] = useState<Agent | null>(null);
  const [properties, setProperties] = useState<PropertyDTO[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const fetchAgentData = useCallback(async (agentId: number) => {
    try {
      setLoading(true);
      setError(null);

      const fetchedAgent = await getAgentById(agentId);
      setAgent(fetchedAgent);

      const response = await getProperties({ page: 0, size: 1000 });
      const filteredProperties = response?.content?.filter(
        (prop: PropertyDTO) =>
          prop.id !== undefined && fetchedAgent.prospectedProperties.includes(prop.id)
      );
      setProperties(filteredProperties || []);
    } catch (err) {
      console.error("Erro ao carregar detalhes do agente ou propriedades:", err);
      setError("Erro ao carregar dados. Por favor, tente novamente.");
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    if (!id) {
      setError("ID não fornecido");
      setLoading(false);
      return;
    }

    const numericId = parseInt(id, 10);
    if (isNaN(numericId)) {
      setError("ID inválido");
      setLoading(false);
      return;
    }

    fetchAgentData(numericId);
  }, [id, fetchAgentData]);

  const handleEdit = () => {
    if (agent?.id) {
      navigate(`/edit-agent/${agent.id}`);
    }
  };

  if (loading) {
    return (
      <div className="flex justify-center items-center py-12 text-gray-600 font-medium">
        Carregando detalhes do agente...
      </div>
    );
  }

  if (error) {
    return (
      <div className="max-w-xl mx-auto p-6 text-red-600 border border-red-300 rounded mt-6 font-semibold">
        {error}
      </div>
    );
  }

  if (!agent) {
    return (
      <div className="max-w-xl mx-auto p-6 text-gray-700 italic mt-6">
        Agente não encontrado.
      </div>
    );
  }

  return (
    <div className="max-w-4xl mx-auto p-6 bg-gray-50 rounded-lg shadow-md mt-6">
      <header className="mb-6 border-b border-gray-300 pb-3">
        <h2 className="text-3xl font-semibold text-gray-800">Detalhes do Agente</h2>
      </header>

      <section className="mb-8 space-y-2 text-gray-800">
        <p>
          <strong>Nome:</strong> {agent.name}
        </p>
        <p>
          <strong>Email:</strong> {agent.email}
        </p>
        <p>
          <strong>Telefone:</strong> {formatPhoneNumber(agent.phone)}
        </p>
        <p>
          <strong>CPF:</strong> {formatCPF(agent.cpf)}
        </p>
        <p>
          <strong>RG:</strong> {agent.rg}
        </p>
        <p>
          <strong>CRECI:</strong> {agent.licenseNumber}
        </p>
        <p>
          <strong>Endereço:</strong> {formatAgentAddress(agent)}
        </p>
        <p>
          <strong>Função:</strong> {RoleLabels[agent.role]}
        </p>
      </section>

      <div className="mb-8">
        <button
          onClick={handleEdit}
          className="px-5 py-2 bg-blue-600 text-white rounded hover:bg-blue-700 transition shadow-md font-semibold"
        >
          Editar
        </button>
      </div>

      <section>
        <h3 className="text-xl font-semibold mb-4 text-gray-800">Imóveis Captados</h3>
        {properties.length > 0 ? (
          <ul className="list-disc list-inside space-y-1 text-gray-700">
            {properties.map((property) => (
              <li key={property.id}>
                <strong>Código do imóvel:</strong> {property.propertyCode}
              </li>
            ))}
          </ul>
        ) : (
          <p className="text-gray-500 italic">Nenhum imóvel captado encontrado.</p>
        )}
      </section>
    </div>
  );
};

export default AgentDetails;
