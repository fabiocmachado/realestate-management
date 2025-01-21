import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { Agent } from "../types/models";
import { getAgentById, updateAgent } from "../services/agentService";
import AgentForm from "./forms/AgentForm";

const EditAgent: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [formData, setFormData] = useState<Agent | null>(null);
  const [error, setError] = useState<string>("");

  useEffect(() => {
    if (id) {
      const fetchAgent = async () => {
        try {
          const agent = await getAgentById(parseInt(id));
          setFormData(agent);
        } catch (error) {
          console.error("Erro ao buscar o agente:", error);
          setError("Erro ao buscar o agente. Tente novamente.");
        }
      };
      fetchAgent();
    }
  }, [id]);

const handleEditAgent = async (agent: Agent) => {
    try {
      await updateAgent(agent.id!, agent);
      alert("Agente editado com sucesso!");
      navigate(`/agents`);
    } catch (error) {
      setError("Erro ao atualizar o agente. Tente novamente.");
      console.error(error);
    }
  };

  const handleCancel = () => {
      navigate(-1);
    };

  if (!formData) {
    return <div>Carregando...</div>;
  }

  return (
        <div>
          <h2>Editar Agente</h2>
          {formData ? <AgentForm agent={formData} onSubmit={handleEditAgent} onCancel={handleCancel} /> : <p>Carregando...</p>}
          {error && <p style={{ color: "red" }}>{error}</p>}
        </div>
  );
  };

export default EditAgent;
