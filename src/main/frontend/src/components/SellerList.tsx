import React, { useEffect, useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { Seller } from "../types/models";
import api from "../services/api";

const removeAccents = (str: string) =>
  str.normalize("NFD").replace(/[\u0300-\u036f]/g, "");

const SellerList: React.FC = () => {
  const [sellers, setSellers] = useState<Seller[]>([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  const navigate = useNavigate();

  useEffect(() => {
    const fetchSellers = async () => {
      try {
        const response = await api.get("/sellers");
        setSellers(response.data);
      } catch (err) {
        console.error("Erro ao carregar os vendedores", err);
        setError("Erro ao carregar os proprietários. Por favor, tente novamente.");
      } finally {
        setLoading(false);
      }
    };

    fetchSellers();
  }, []);

  const handleGoToDetails = (id: number) => {
    navigate(`/sellers/${id}`);
  };

  const filteredSellers = sellers
    .sort((a, b) => (b.id ?? 0) - (a.id ?? 0))
    .filter((seller) =>
      removeAccents(seller.name.toLowerCase()).includes(removeAccents(searchTerm.toLowerCase()))
    );

  if (loading)
    return <div className="text-primary-light font-medium">Carregando lista de proprietários...</div>;
  if (error)
    return <div className="text-red-600 font-semibold">{error}</div>;

  return (
    <div className="max-w-4xl mx-auto p-6 bg-gray-light rounded-lg shadow-md">
      <div className="flex justify-between items-center mb-6">
        <h2 className="text-2xl font-bold text-primary-dark">Lista de Proprietários</h2>
        <Link
          to="/add-seller"
          className="bg-primary DEFAULT hover:bg-primary-dark text-white font-semibold py-2 px-4 rounded shadow transition-colors"
        >
          Cadastrar Proprietário
        </Link>
      </div>

      <div className="mb-4">
        <label htmlFor="searchName" className="block font-semibold mb-1">
          Buscar por Nome:
        </label>
        <input
          id="searchName"
          type="text"
          placeholder="Digite o nome..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          className="w-full p-2 border border-gray-pale rounded focus:outline-none focus:ring-2 focus:ring-primary"
        />
      </div>

      <table className="w-full border-collapse bg-white rounded shadow-sm">
        <thead className="bg-primary text-white">
          <tr>
            <th className="text-left p-3">Id</th>
            <th className="text-left p-3">Nome</th>
            <th className="text-left p-3">Email</th>
            <th className="text-left p-3">Telefone</th>
            <th className="text-center p-3">Ações</th>
          </tr>
        </thead>
        <tbody>
          {filteredSellers.length > 0 ? (
            filteredSellers.map((seller) => (
              <tr key={seller.id} className="border-b last:border-b-0 hover:bg-gray-50">
                <td className="p-3">{seller.id}</td>
                <td className="p-3">{seller.name}</td>
                <td className="p-3">{seller.email}</td>
                <td className="p-3">{seller.phone}</td>
                <td className="p-3 text-center">
                  <button
                    onClick={() => handleGoToDetails(seller.id!)}
                    className="bg-primary DEFAULT hover:bg-primary-dark text-white font-semibold py-1 px-3 rounded transition-colors"
                  >
                    Detalhes
                  </button>
                </td>
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan={5} className="text-center p-4 italic text-gray-600">
                Nenhum proprietário encontrado.
              </td>
            </tr>
          )}
        </tbody>
      </table>
    </div>
  );
};

export default SellerList;
