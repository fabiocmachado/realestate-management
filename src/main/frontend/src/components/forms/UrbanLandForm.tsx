import React, { useState, useEffect } from 'react';
import { useNavigate } from "react-router-dom";
import { createUrbanLand, updateUrbanLand } from '../../services/urbanLandService';
import { getAgents } from "../../services/agentService";
import { getSellers } from "../../services/sellerService";
import { UrbanLandDTO, Agent, Seller, PropertyStatus, PropertyType,PropertyStatusDescription } from "../../types/models";
import "../../styles/propertiesForms.css";

const formatPrice = (value: string) => {
  let number = value.replace(/[^\d,]/g, '');
  const [integer, decimal] = number.split(',');
  const formattedInteger = integer ? integer.replace(/\B(?=(\d{3})+(?!\d))/g, '.') : '';
  const formattedDecimal = decimal ? decimal.substring(0, 2) : '';
  return formattedDecimal ? `${formattedInteger},${formattedDecimal}` : formattedInteger;
};

interface UrbanLandFormProps {
  initialData?: UrbanLandDTO;
  onSuccess: () => void;
  onCancel: () => void;
}

const defaultUrbanLand: UrbanLandDTO = {
  id: 0,
  propertyCode: '',
  propertyType: PropertyType.URBAN,
  price: 0,
  street: '',
  block: '',
  lot: '',
  complement: '',
  number: '',
  neighborhood: '',
  city: '',
  state: '',
  placeOfKeys: '',
  orientation: '',
  description: '',
  status: PropertyStatus.AVAILABLE,
  createdAt: '',
  updatedAt: '',
  visitingTime: '',
  propertyCategory: 'URBAN_LAND',
  usableArea: 0,
  privateArea: 0,
  totalArea: 0,
  agentId: 0,
  sellerId: 0,
  bedrooms: undefined,
  bathrooms: undefined,
  garageSpaces: undefined,
  hasKitchen: undefined,
  hasWall: false,
  hasAsphalt: false,
};

const UrbanLandForm: React.FC<UrbanLandFormProps> = ({ initialData, onSuccess, onCancel }) => {
  const [urbanLand, setUrbanLand] = useState<UrbanLandDTO>(() => ({ ...defaultUrbanLand, ...initialData }));
  const [agents, setAgents] = useState<Agent[]>([]);
  const [sellers, setSellers] = useState<Seller[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [successMessage, setSuccessMessage] = useState<string | null>(null);
  const navigate = useNavigate();
  const isEditMode = !!initialData;

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [agentsResponse, sellersResponse] = await Promise.all([
          getAgents(),
          getSellers()
        ]);
        setAgents(agentsResponse);
        setSellers(sellersResponse);
      } catch (err) {
        setError('Erro ao carregar agentes e vendedores.');
      }
    };

    fetchData();
  }, []);


  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>) => {
    const { name, value, type } = e.target;
    let formattedValue: any = value;

    if (name === "price") {
      formattedValue = value
        ? parseFloat(value.replace(/\./g, "").replace(",", "."))
        : 0;
    } else if (type === "number") {
      formattedValue = value === "" ? "" : Number(value);
    } else if (name === "hasWall" || name === "hasAsphalt") {
      formattedValue = value === "Sim";
    }

    setUrbanLand((prev) => ({
      ...prev,
      [name]: formattedValue,
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setError(null);
    setSuccessMessage(null);

    try {
      if (isEditMode) {
        await updateUrbanLand(urbanLand.propertyCode ?? '', urbanLand);
        setSuccessMessage('Terreno urbano atualizado com sucesso!');
      } else {
        const { id, propertyCode, createdAt, updatedAt, ...urbanLandData } = urbanLand;
        await createUrbanLand(urbanLandData);
        setSuccessMessage('Terreno urbano criado com sucesso!');
      }
      onSuccess();
    } catch (err) {
      setError(`Erro ao ${isEditMode ? 'atualizar' : 'criar'} o terreno urbano.`);
    } finally {
      setLoading(false);
    }
  };

   const print = () => {
        navigate('/print/urban_land');
      };

  return (
    <div className="form-container">
      <h2>{isEditMode ? 'Editar' : 'Cadastrar'} Terreno Urbano</h2>
      {error && <div className="error-message">{error}</div>}
      {successMessage && <div className="success-message">{successMessage}</div>}
      <div className="form-header"><button onClick={() => print()}>Imprimir ficha</button></div>
      <form onSubmit={handleSubmit}>
        <FormField label="Preço" type="text" name="price" value={urbanLand.price} onChange={handleChange}  />
                  <FormField label="Rua" type="text" name="street" value={urbanLand.street} onChange={handleChange}  />
                  <FormField label="Quadra" type="text" name="block" value={urbanLand.block} onChange={handleChange}  />
                  <FormField label="Lote" type="text" name="lot" value={urbanLand.lot} onChange={handleChange}  />
                  <FormField label="Número" type="text" name="number" value={urbanLand.number} onChange={handleChange}  />
                  <FormField label="Complemento" type="text" name="complement" value={urbanLand.complement} onChange={handleChange}  />
                  <FormField label="Bairro" type="text" name="neighborhood" value={urbanLand.neighborhood} onChange={handleChange}  />
                  <FormField label="Cidade" type="text" name="city" value={urbanLand.city} onChange={handleChange}  />
                  <FormField label="Estado" type="text" name="state" value={urbanLand.state} onChange={handleChange}  />
                  <FormField label="Orientação" type="text" name="orientation" value={urbanLand.orientation} onChange={handleChange}  />
        <FormField
          label="Área Total (m²)"
          type="number"
          name="totalArea"
          value={urbanLand.totalArea}
          onChange={handleChange}
          required
        />

        <FormField
          label="Local das Chaves"
          type="text"
          name="placeOfKeys"
          value={urbanLand.placeOfKeys}
          onChange={handleChange}
        />
        <SelectField
          label="Possui Muro"
          name="hasWall"
          value={urbanLand.hasWall ? 'Sim' : 'Não'}
          options={[
            { id: 'Sim', name: 'Sim' },
            { id: 'Não', name: 'Não' },
          ]}
          onChange={handleChange}
        />
        <SelectField
          label="Possui Asfalto"
          name="hasAsphalt"
          value={urbanLand.hasAsphalt ? 'Sim' : 'Não'}
          options={[
            { id: 'Sim', name: 'Sim' },
            { id: 'Não', name: 'Não' },
          ]}
          onChange={handleChange}
        />
        <FormField
          label="Descrição"
          type="textarea"
          name="description"
          value={urbanLand.description || ''}
          onChange={handleChange}
        />
      <SelectField
         label="Status da Propriedade"
         name="status"
         value={urbanLand.status}
         options={Object.entries(PropertyStatus).map(([key, value]) => ({
           id: value,
           name: PropertyStatusDescription[value] || key
         }))}
         onChange={handleChange}
        />
        <SelectField
          label="Agente"
          name="agentId"
          value={urbanLand.agentId?.toString() || ''}
          options={[
            { id: '', name: 'Selecione...' },
            ...agents.map(agent => ({
              id: agent.id !== undefined ? agent.id.toString() : '',
              name: `${agent.id} - ${agent.name}`,
            })),
          ]}
          onChange={handleChange}
          required
        />
        <SelectField
          label="Vendedor"
          name="sellerId"
          value={urbanLand.sellerId?.toString() || ''}
          options={[
            { id: '', name: 'Selecione...' },
            ...[...sellers]
            .sort((a, b) => (b.id || 0) - (a.id || 0))
            .map(seller => ({
              id: seller.id !== undefined ? seller.id.toString() : '',
              name: `${seller.id ?? 'Sem ID'} - ${seller.name}`,
            })),
          ]}
          onChange={handleChange}
          required
        />
        <button type="submit" disabled={loading}>
          {loading ? 'Carregando...' : isEditMode ? 'Salvar Alterações' : 'Adicionar Terreno'}
        </button>
        <button
          type="button"
          className="cancel-button"
          onClick={onCancel}
        >
          Cancelar
        </button>
      </form>
    </div>
  );
};

interface FormFieldProps {
  label: string;
  type: string;
  name: string;
  value: string | number;
  onChange: (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => void;
  required?: boolean;
}

const FormField: React.FC<FormFieldProps> = ({
  label,
  type,
  name,
  value,
  onChange,
  required
}) => (
  <div className="form-group">
    <label>{label}</label>
    {type === 'textarea' ? (
      <textarea
        name={name}
        value={value}
        onChange={onChange}
        required={required}
      />
    ) : (
      <input
        type={type}
        name={name}
        value={value}
        onChange={onChange}
        required={required}
      />
    )}
  </div>
);

interface SelectFieldProps {
  label: string;
  name: string;
  value: string;
  options: { id: string; name: string; }[];
  onChange: (e: React.ChangeEvent<HTMLSelectElement>) => void;
  required?: boolean;
}

const SelectField: React.FC<SelectFieldProps> = ({
  label,
  name,
  value,
  options,
  onChange,
  required
}) => (
  <div className="form-group">
    <label>{label}</label>
    <select
      name={name}
      value={value}
      onChange={onChange}
      required={required}
    >
      {options.map(option => (
        <option key={option.id} value={option.id}>
          {option.name}
        </option>
      ))}
    </select>
  </div>
);

export default UrbanLandForm;
