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

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
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

  return (
    <form onSubmit={handleSubmit} className="form">
      {([
        { name: "name", type: "text", label: "Nome", required: true },
        { name: "cpf", type: "text", label: "CPF" },
        { name: "rg", type: "text", label: "RG" },
        { name: "email", type: "email", label: "Email" },
        { name: "phone", type: "text", label: "Telefone" },
        { name: "street", type: "text", label: "Rua" },
        { name: "block", type: "text", label: "Bloco" },
        { name: "lot", type: "text", label: "Lote" },
        { name: "complement", type: "text", label: "Complemento" },
        { name: "number", type: "text", label: "Número" },
        { name: "neighborhood", type: "text", label: "Bairro" },
        { name: "city", type: "text", label: "Cidade" },
        { name: "state", type: "text", label: "Estado" },
        { name: "licenseNumber", type: "text", label: "CRECI" },
        { name: "role", type: "select", label: "Função" },
        { name: "password", type: "password", label: "Senha" },
      ]).map(({ name, type, label, required = false }) => (
        <div className="form-group" key={name}>
          <label htmlFor={name}>{label}</label>
          {name === "password" ? (
            <div className="password-group">
              <input
                type={showPassword ? "text" : "password"}
                id={name}
                name={name}
                value={formData.password || ""}
                onChange={handleChange}
                placeholder={agent ? "Deixe vazio para manter a senha atual" : "Digite uma senha"}
                className="form-control"
              />
              <button
                type="button"
                onClick={togglePasswordVisibility}
                className="password-toggle"
              >
                {showPassword ? "Ocultar senha" : "Mostrar senha"}
              </button>
            </div>
          ) : type === "select" ? (
            <select
              id={name}
              name={name}
              value={formData[name as keyof Agent] as string}
              onChange={handleChange}
              className="form-control"
            >
              {Object.entries(RoleLabels).map(([role, label]) => (
                <option key={role} value={role}>
                  {label}
                </option>
              ))}
            </select>
          ) : (
            <input
              type={type}
              id={name}
              name={name}
              value={formData[name as keyof Agent] as string}
              onChange={handleChange}
              required={required}
              className="form-control"
            />
          )}
        </div>
      ))}

      <button type="submit" className="btn btn-primary">
        {agent ? "Salvar" : "Cadastrar"}
      </button>
      <button type="button" className="cancel-button" onClick={onCancel}>
        Cancelar
      </button>
    </form>
  );
};

export default AgentForm;