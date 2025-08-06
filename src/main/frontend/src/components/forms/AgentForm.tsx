import React, { useState, useEffect } from "react";
import { Agent, RoleLabels, UserRole } from "../../types/models";
import { formatPhoneNumber } from "../../components/shared/shared";

interface AgentFormProps {
  agent?: Agent;
  onSubmit: (agent: Agent) => void;
  onCancel: () => void;
}

const AgentForm: React.FC<AgentFormProps> = ({ agent, onSubmit, onCancel }) => {
  const [formData, setFormData] = useState<Agent>({
    id: 0,
    name: "",
    cpf: "",
    rg: "",
    email: "",
    phone: "",
    street: "",
    block: "",
    lot: "",
    complement: "",
    number: "",
    neighborhood: "",
    city: "",
    state: "",
    licenseNumber: "",
    role: UserRole.AGENT,
    password: "",
    hasAdminPermissions: false,
    prospectedProperties: [],
    createdAt: "",
    updatedAt: "",
  });

  const [showPassword, setShowPassword] = useState(false);

  useEffect(() => {
    if (agent) {
      setFormData({
        ...agent,
        role: agent.role || UserRole.AGENT,
        password: agent.password || "",
        hasAdminPermissions: agent.hasAdminPermissions || false,
      });
    }
  }, [agent]);

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>
  ) => {
    const { name, value, type } = e.target;
    const checked = (e.target as HTMLInputElement).checked;

    setFormData((prevState) => {
      const updatedData = {
        ...prevState,
        [name]: type === "checkbox" ? checked : value,
      };
      if (name === "role") {
        updatedData.hasAdminPermissions = value === UserRole.ADMIN;
      }

      return updatedData;
    });
  };

  const togglePasswordVisibility = () => {
    setShowPassword(!showPassword);
  };

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const cleanedFormData: Agent = {
      ...formData,
      phone: formatPhoneNumber(formData.phone),
    };

    onSubmit(cleanedFormData);
  };

  // Campos gerais (não-endereço)
  const generalFields = [
    { name: "name", type: "text", label: "Nome", required: true },
    { name: "cpf", type: "text", label: "CPF" },
    { name: "rg", type: "text", label: "RG" },
    { name: "email", type: "email", label: "Email" },
    { name: "phone", type: "text", label: "Telefone" },
    { name: "licenseNumber", type: "text", label: "CRECI" },
    { name: "role", type: "select", label: "Função" },
    { name: "password", type: "password", label: "Senha" },
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

  const renderField = ({
    name,
    type,
    label,
    required = false,
  }: {
    name: string;
    type: string;
    label: string;
    required?: boolean;
  }) => {
    if (name === "password") {
      return (
        <div className="relative">
          <input
            type={showPassword ? "text" : "password"}
            id={name}
            name={name}
            value={formData.password || ""}
            onChange={handleChange}
            placeholder={
              agent
                ? "Deixe vazio para manter a senha atual"
                : "Digite uma senha"
            }
            className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
          />
          <button
            type="button"
            onClick={togglePasswordVisibility}
            className="absolute inset-y-0 right-3 flex items-center text-sm text-blue-600 hover:text-blue-800"
          >
            {showPassword ? "Ocultar" : "Mostrar"}
          </button>
        </div>
      );
    }

    if (type === "select") {
      return (
        <select
          id={name}
          name={name}
          value={formData[name as keyof Agent] as string}
          onChange={handleChange}
          className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
        >
          {Object.entries(RoleLabels).map(([role, label]) => (
            <option key={role} value={role}>
              {label}
            </option>
          ))}
        </select>
      );
    }

    return (
      <input
        type={type}
        id={name}
        name={name}
        value={formData[name as keyof Agent] as string}
        onChange={handleChange}
        required={required}
        className="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-blue-500"
      />
    );
  };

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
              {renderField({ name, type, label, required })}
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
              {renderField({ name, type, label })}
            </div>
          ))}
        </div>
      </div>

      <div className="flex justify-end space-x-3 mt-6">
        <button
          type="submit"
          className="px-5 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700 transition"
        >
          {agent ? "Salvar" : "Cadastrar"}
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

export default AgentForm;
