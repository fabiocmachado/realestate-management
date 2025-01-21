import React, { useState } from "react";
import { Agent } from "../types/models";
import { createAgent } from "../services/agentService";
import AgentForm from "./forms/AgentForm";
import { useNavigate } from "react-router-dom";

const AddAgent: React.FC = () => {
  const [error, setError] = useState<string>("");
  const navigate = useNavigate();

  const handleAddAgent = async (agent: Agent) => {
    try {
      await createAgent(agent);
      alert("Agente criado com sucesso!");
      navigate(`/agents`);
    } catch (error) {
      setError("Erro ao criar o agente. Tente novamente.");
      console.error(error);
    }
  };

const handleCancel = () => {
    navigate(-1);
  };

   return (
      <div>
        <h2>Cadastrar Agente</h2>
        <AgentForm agent={undefined} onSubmit={handleAddAgent} onCancel={handleCancel}/>
        {error && <p style={{ color: "red" }}>{error}</p>}
      </div>
    );
  };

export default AddAgent;
