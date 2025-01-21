import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { Seller } from "../types/models";
import { getSellerById, updateSeller } from "../services/sellerService";
import SellerForm from "./forms/SellerForm";

const EditSeller: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [formData, setFormData] = useState<Seller | null>(null);
  const [error, setError] = useState<string>("");

  useEffect(() => {
    if (id) {
      const fetchSeller = async () => {
        try {
          const seller = await getSellerById(parseInt(id));
          setFormData(seller);
        } catch (error) {
          console.error("Erro ao buscar o proprietário:", error);
          setError("Erro ao buscar o proprietário. Tente novamente.");
        }
      };
      fetchSeller();
    }
  }, [id]);

  const handleEditSeller = async (seller: Seller) => {
    try {
      await updateSeller(seller.id!, seller);
      alert("Vendedor editado com sucesso!");
      navigate(`/sellers`);
    } catch (error) {
      setError("Erro ao atualizar o vendedor. Tente novamente.");
      console.error(error);
    }
  };

  const handleCancel = () => {
      navigate(-1);
    };

    return (
      <div>
        <h2>Editar Proprietário</h2>
{formData ? <SellerForm seller={formData} onSubmit={handleEditSeller} onCancel={handleCancel} /> : <p>Carregando...</p>}        {error && <p style={{ color: "red" }}>{error}</p>}
      </div>
    );
    };

export default EditSeller;