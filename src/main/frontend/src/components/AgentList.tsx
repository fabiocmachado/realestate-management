import React, { useEffect, useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { Agent, RoleLabels } from "../types/models";
import api from "../services/api";
import "../styles/agentList.css"


const AgentList: React.FC = () => {
  const [agents, setAgents] = useState<Agent[]>([]);
    const navigate = useNavigate();

  useEffect(() => {
    api
      .get("/agents")
      .then((response) => setAgents(response.data))
      .catch((error) => console.error("Erro ao carregar os agentes", error));
  }, []);

    const handleEdit = (id: number) => {
        navigate(`/edit-agent/${id}`);
      };

    const goToDetails = (id: number) => {
      navigate(`/agents/${id}`);
    };

  return (
    <div>
     <div className="add-agent-link">
      <Link to="/add-agent">Cadastrar Agente</Link>
    </div>
      <h2>Lista de Agentes</h2>
      <table>
        <thead>
          <tr>
           <th>Id</th>
            <th>Nome</th>
            <th>Email</th>
            <th>Telefone</th>
            <th>Função</th>
            <th>Ações</th>
          </tr>
        </thead>
        <tbody>
          {agents.map((agent) => (
            <tr key={agent.id}>
              <td>{agent.id}</td>
              <td>{agent.name}</td>
              <td>{agent.email}</td>
              <td>{agent.phone}</td>
              <td>{RoleLabels[agent.role]}</td>
              <td>
                <button onClick={() => agent.id && handleEdit(agent.id)}>Editar</button>
                <button onClick={() => goToDetails(agent.id!)}>Detalhes</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default AgentList;
