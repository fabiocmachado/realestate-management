import React, { useState, useEffect } from "react";
import { Seller } from "../../types/models";
import { formatPhoneNumber } from "../../components/shared/shared";

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
    setFormData((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const cleanedFormData: Seller = {
      ...formData,
      phone: formatPhoneNumber(formData.phone),
    };

    onSubmit(cleanedFormData);
  };

  // Campos gerais (não endereço)
  const generalFields = [
    { name: "name", type: "text", label: "Nome", required: true },
    { name: "cpf", type: "text", label: "CPF" },
    { name: "rg", type: "text", label: "RG" },
    { name: "email", type: "email", label: "Email" },
    { name: "phone", type: "text", label: "Telefone" },
  ];

  // Campos de endereço
  const addressFields = [
    { name: "street", type: "text", label: "Rua" },
    { name: "block", type: "text", label: "Bloco" },
    { name: "lot", type: "text", label: "Lote" },
    { name: "complement", type: "text", label: "Complemento" },
    { name: "number", type: "text", label: "Número" },
    { name: "neighborhood", type: "text", label: "Bairro" },
    { name: "city", type: "text", label: "Cidade" },
    { name: "state", type: "text", label: "Estado" },
  ];

  return (
    <form
      onSubmit={handleSubmit}
      className="max-w-4xl mx-auto p-6 bg-white rounded-md shadow-md"
    >
      <div className="flex flex-col md:flex-row md:space-x-8">
        {/* Coluna geral */}
        <div className="flex-1">
          {generalFields.map(({ name, type, label, required }) => (
            <div key={name} className="mb-5">
              <label
                htmlFor={name}
                className="block mb-1 font-semibold text-gray-700"
              >
                {label}
              </label>
              <input
                type={type}
                id={name}
                name={name}
                value={formData[name as keyof Seller] as string}
                onChange={handleChange}
                required={required}
                className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
              />
            </div>
          ))}
        </div>

        {/* Coluna endereço */}
        <div className="flex-1 mt-8 md:mt-0">
          <h3 className="mb-4 text-lg font-semibold text-gray-800">Endereço</h3>
          {addressFields.map(({ name, type, label }) => (
            <div key={name} className="mb-5">
              <label
                htmlFor={name}
                className="block mb-1 font-semibold text-gray-700"
              >
                {label}
              </label>
              <input
                type={type}
                id={name}
                name={name}
                value={formData[name as keyof Seller] as string}
                onChange={handleChange}
                className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
              />
            </div>
          ))}
        </div>
      </div>

      <div className="flex justify-end space-x-3 mt-6">
        <button
          type="submit"
          className="px-5 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 transition"
        >
          {seller ? "Salvar" : "Cadastrar"}
        </button>
        <button
          type="button"
          onClick={onCancel}
          className="px-5 py-2 border border-gray-300 rounded-md hover:bg-gray-100 transition"
        >
          Cancelar
        </button>
      </div>
    </form>
  );
};

export default SellerForm;
