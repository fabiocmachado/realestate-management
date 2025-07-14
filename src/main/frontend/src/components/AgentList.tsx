import React, { useEffect, useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { Agent, RoleLabels } from "../types/models";
import api from "../services/api";

const AgentList: React.FC = () => {
  const [agents, setAgents] = useState<Agent[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const navigate = useNavigate();

  useEffect(() => {
    const fetchAgents = async () => {
      try {
        const response = await api.get("/agents");
        setAgents(response.data);
      } catch (err) {
        console.error("Erro ao carregar os agentes", err);
        setError("Erro ao carregar os corretores. Por favor, tente novamente.");
      } finally {
        setLoading(false);
      }
    };
    fetchAgents();
  }, []);

  const handleGoToDetails = (id: number) => {
    navigate(`/agents/${id}`);
  };

  const sortedAgents = [...agents].sort((a, b) => (b.id ?? 0) - (a.id ?? 0));

  if (loading)
    return (
      <div className="text-primary-light font-medium">
        Carregando lista de corretores...
      </div>
    );
  if (error)
    return (
      <div className="text-red-600 font-semibold">
        {error}
      </div>
    );

  return (
    <div className="max-w-4xl mx-auto p-6 bg-gray-light rounded-lg shadow-md">
      <div className="flex justify-between items-center mb-6">
        <h2 className="text-2xl font-bold text-primary-dark">Lista de Corretores</h2>
        <Link
          to="/add-agent"
          className="bg-primary DEFAULT hover:bg-primary-dark text-white font-semibold py-2 px-4 rounded shadow transition-colors"
        >
          Cadastrar Corretor
        </Link>
      </div>

      <div className="overflow-x-auto">
        <table className="w-full border-collapse bg-white rounded shadow-sm">
          <thead className="bg-primary text-white">
            <tr>
              <th className="text-left p-3">Id</th>
              <th className="text-left p-3">Nome</th>
              <th className="text-left p-3">Email</th>
              <th className="text-left p-3">Telefone</th>
              <th className="text-left p-3">Função</th>
              <th className="text-center p-3">Ações</th>
            </tr>
          </thead>
          <tbody>
            {sortedAgents.length > 0 ? (
              sortedAgents.map((agent) => (
                <tr
                  key={agent.id}
                  className="border-b last:border-b-0 hover:bg-gray-50 cursor-pointer"
                  onClick={() => handleGoToDetails(agent.id!)}
                >
                  <td className="p-3">{agent.id}</td>
                  <td className="p-3">{agent.name}</td>
                  <td className="p-3">{agent.email}</td>
                  <td className="p-3">{agent.phone}</td>
                  <td className="p-3">{RoleLabels[agent.role]}</td>
                  <td className="p-3 text-center">
                    <button
                      onClick={(e) => {
                        e.stopPropagation();
                        handleGoToDetails(agent.id!);
                      }}
                      className="bg-primary DEFAULT hover:bg-primary-dark text-white font-semibold py-1 px-3 rounded transition-colors"
                    >
                      Detalhes
                    </button>
                  </td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan={6} className="text-center p-4 italic text-gray-600">
                  Nenhum corretor encontrado.
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default AgentList;
