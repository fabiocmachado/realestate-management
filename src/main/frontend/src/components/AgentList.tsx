import React, { useEffect, useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { Agent, RoleLabels } from "../types/models";
import api from "../services/api";
import '../styles/agentList.css'

const AgentList: React.FC = () => {
  const [agents, setAgents] = useState<Agent[]>([]);
  const navigate = useNavigate();

  useEffect(() => {
    api
      .get("/agents")
      .then((response) => setAgents(response.data))
      .catch((error) => console.error("Erro ao carregar os agentes", error));
  }, []);


  const goToDetails = (id: number) => {
    navigate(`/agents/${id}`);
  };

  const sortedAgents = agents.sort((a, b) => (b.id ?? 0) - (a.id ?? 0));

  return (
    <div>
      <div className="add-agent-link">
        <Link to="/add-agent">Cadastrar Corretor</Link>
      </div>
      <h2>Lista de Corretores</h2>
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
          {sortedAgents.map((agent) => (
            <tr key={agent.id}>
              <td>{agent.id}</td>
              <td>{agent.name}</td>
              <td>{agent.email}</td>
              <td>{agent.phone}</td>
              <td>{RoleLabels[agent.role]}</td>
              <td>
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
