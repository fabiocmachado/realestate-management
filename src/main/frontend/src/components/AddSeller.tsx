import React, { useState } from "react";
import { Seller } from "../types/models";
import { createSeller } from "../services/sellerService";
import SellerForm from "./forms/SellerForm";
import { useNavigate } from "react-router-dom";

const AddSeller: React.FC = () => {
  const [error, setError] = useState<string>("");
  const navigate = useNavigate();

  const handleAddSeller = async (seller: Seller) => {
    try {
      await createSeller(seller);
      alert("Proprietário criado com sucesso!");
      navigate(`/sellers`);
    } catch (error) {
      setError("Erro ao criar o proprietário. Tente novamente.");
      console.error(error);
    }
  };

  const handleCancel = () => {
      navigate(-1);
    };

  return (
    <div>
      <h2>Cadastrar Proprietário</h2>
      <SellerForm seller={undefined} onSubmit={handleAddSeller} onCancel={handleCancel} />
      {error && <p style={{ color: "red" }}>{error}</p>}
    </div>
  );
};

export default AddSeller;
