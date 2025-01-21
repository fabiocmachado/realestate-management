import React, { useEffect, useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { Seller } from "../types/models";
import api from "../services/api";
import "../styles/sellerList.css";

const removeAccents = (str: string) => {
  return str.normalize("NFD").replace(/[\u0300-\u036f]/g, "");
};

const SellerList: React.FC = () => {
  const [sellers, setSellers] = useState<Seller[]>([]);
  const [searchTerm, setSearchTerm] = useState<string>("");
  const navigate = useNavigate();

  useEffect(() => {
    api
      .get("/sellers")
      .then((response) => setSellers(response.data))
      .catch((error) => console.error("Erro ao carregar os vendedores", error));
  }, []);

  const handleEdit = (id: number) => {
    navigate(`/edit-seller/${id}`);
  };

  const goToDetails = (id: number) => {
    navigate(`/sellers/${id}`);
  };

  const filteredSellers = sellers.filter((seller) =>
      removeAccents(seller.name.toLowerCase()).includes(removeAccents(searchTerm.toLowerCase()))
    );

  return (
    <div>
     <div className="add-seller-link">
       <Link to="/add-seller">Cadastrar Proprietário</Link>
     </div>
      <h2>Lista de Proprietários</h2>
      <div className="search-group">
      <div className="search-container">
        <label htmlFor="searchCode">Buscar por Nome:</label>
         <input
            type="text"
            placeholder="Digite o nome..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
      </div>
      </div>
      <table>
        <thead>
          <tr>
            <th>Id</th>
            <th>Nome</th>
            <th>Email</th>
            <th>Telefone</th>
            <th>Ações</th>
          </tr>
        </thead>
        <tbody>
          {filteredSellers.map((seller) => (
            <tr key={seller.id}>
              <td>{seller.id}</td>
              <td>{seller.name}</td>
              <td>{seller.email}</td>
              <td>{seller.phone}</td>
              <td>
                <button onClick={() => handleEdit(seller.id!)}>Editar</button>
                <button onClick={() => goToDetails(seller.id!)}>Detalhes</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default SellerList;