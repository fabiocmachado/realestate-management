import React from "react";
import { useNavigate, useParams } from "react-router-dom";
import { deleteAgent } from "../services/agentService";

const DeleteAgent: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const handleDelete = async () => {
    const confirmDelete = window.confirm("Tem certeza de que deseja excluir este agente?");
    if (!confirmDelete) return;

    try {
      if (id) {
        await deleteAgent(Number(id));
        navigate("/agents");
      }
    } catch (error) {
      console.error("Erro ao excluir agente:", error);
    }
  };

  return (
    <div>
      <h1>Excluir Agente</h1>
      <button onClick={handleDelete}>Confirmar Excluir</button>
    </div>
  );
};


export default DeleteAgent;
