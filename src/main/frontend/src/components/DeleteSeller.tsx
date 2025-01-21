import React, { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { deleteSeller } from "../services/sellerService";

const DeleteSeller: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [error, setError] = useState<string | null>(null);

  const handleDelete = async () => {
    const confirmDelete = window.confirm("Tem certeza de que deseja excluir este vendedor?");
    if (!confirmDelete) return;

    try {
      if (id) {
        await deleteSeller(Number(id));
        navigate("/sellers");
      }
    } catch (error) {
      console.error("Erro ao excluir vendedor:", error);
      if (error instanceof Error) {
        setError(error.message || "Erro ao excluir o vendedor. Tente novamente mais tarde.");
      } else {
        setError("Erro desconhecido ao excluir o vendedor.");
      }
    }
  };

  return (
      <div>
        <h1>Excluir Vendedor</h1>
        <button onClick={handleDelete} style={{ marginRight: "1rem" }}>
          Confirmar Exclusão
        </button>
        <button onClick={() => navigate("/sellers")}>Cancelar</button>
      </div>
    );
  };

  export default DeleteSeller;