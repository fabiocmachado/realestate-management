import React, { useState, useEffect } from "react";
import { Seller } from "../../types/models";

interface SellerFormProps {
  seller?: Seller;
  onSubmit: (seller: Seller) => void;
  onCancel: () => void;
}

const SellerForm: React.FC<SellerFormProps> = ({ seller, onSubmit, onCancel }) => {
  const [formData, setFormData] = useState<Seller>({
    id: seller?.id || 0,
    name: seller?.name || "",
    cpf: seller?.cpf || "",
    rg: seller?.rg || "",
    email: seller?.email || "",
    phone: seller?.phone || "",
    street: seller?.street || "",
    block: seller?.block || "",
    lot: seller?.lot || "",
    complement: seller?.complement || "",
    number: seller?.number || "",
    neighborhood: seller?.neighborhood || "",
    city: seller?.city || "",
    state: seller?.state || "",
    properties: seller?.properties || [],
    createdAt: seller?.createdAt || "",
    updatedAt: seller?.updatedAt || "",
  });

  useEffect(() => {
    if (seller) {
      setFormData(seller);
    }
  }, [seller]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    onSubmit(formData);
  };

  return (
    <form onSubmit={handleSubmit} className="form">
      {([
        { name: "name", type: "text", label: "Nome", required: true },
        { name: "cpf", type: "text", label: "CPF",  },
        { name: "rg", type: "text", label: "RG", },
        { name: "email", type: "email", label: "Email",  },
        { name: "phone", type: "text", label: "Telefone",  },
        { name: "street", type: "text", label: "Rua",  },
        { name: "block", type: "text", label: "Bloco" },
        { name: "lot", type: "text", label: "Lote" },
        { name: "complement", type: "text", label: "Complemento" },
        { name: "number", type: "text", label: "Número", },
        { name: "neighborhood", type: "text", label: "Bairro",  },
        { name: "city", type: "text", label: "Cidade", },
        { name: "state", type: "text", label: "Estado",  },

      ]).map(({ name, type, label, required, }) => (
        <div className="form-group" key={name}>
          <label htmlFor={name}>{label}</label>
          <input
            type={type}
            id={name}
            name={name}
            value={formData[name as keyof Seller] as string}
            required={required}
            className="form-control"
          />
        </div>
      ))}
      <button type="submit" className="btn btn-primary">{seller ? "Salvar" : "Cadastrar"}</button>
      <button type="button" className="cancel-button" onClick={onCancel}>Cancelar</button>
    </form>
  );
};

export default SellerForm;