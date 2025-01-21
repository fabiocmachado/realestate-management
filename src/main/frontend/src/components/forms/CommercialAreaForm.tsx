import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { CommercialAreaDTO, PropertyStatusDescription, Agent, Seller, PropertyStatus } from "../../types/models";
import { getAgents } from "../../services/agentService";
import { getSellers } from "../../services/sellerService";
import { createCommercialArea, updateCommercialArea, getCommercialAreaByCode } from "../../services/commercialAreaService";
import "../../styles/propertiesForms.css";
import { formatCPF, formatPhoneNumber } from "../../components/shared/shared";

const formatPrice = (value: string) => {
  let number = value.replace(/[^\d,]/g, '');
  const [integer, decimal] = number.split(',');
  const formattedInteger = integer ? integer.replace(/\B(?=(\d{3})+(?!\d))/g, '.') : '';
  const formattedDecimal = decimal ? decimal.substring(0, 2) : '';
  return formattedDecimal ? `${formattedInteger},${formattedDecimal}` : formattedInteger;
};

interface CommercialAreaFormProps {
  initialData?: CommercialAreaDTO;
  onSuccess: () => void;
  onCancel?: () => void;
}

const defaultCommercialArea: CommercialAreaDTO = {
  yearsOfConstruction: 0,
  isRented: false,
  rentalValue: 0,
  isInhabited: false,
  hasAirConditioning: false,
  floorType: '',
  hasSurveillanceCameras: false,
  condominiumFee: 0,
  nameOfBuilding: '',
  offices: 0,
  hasMezzanine: false,
  hasKitchen: false,
  garageSpaces: 0,
  hasGaragesInRow: false,
  hasSolarEnergy: false,
  hasWall: false,
  hasAsphalt: false,
  id: 0,
  propertyCode: '',
  price: 0,
  street: '',
  block: '',
  lot: '',
  complement: '',
  number: '',
  neighborhood: '',
  city: '',
  state: '',
  description: '',
  status: PropertyStatus.AVAILABLE,
  createdAt: '',
  updatedAt: '',
  propertyCategory: 'COMMERCIAL_AREA',
  agentId: 0,
  sellerId: 0,
  totalArea: 0,
  privateArea: 0,
  usableArea: 0,
  placeOfKeys: '',
  orientation: '',
  visitingTime: '',
};

const CommercialAreaForm: React.FC<CommercialAreaFormProps> = ({ initialData, onSuccess, onCancel }) => {
  const [commercialArea, setCommercialArea] = useState<CommercialAreaDTO>(() => ({ ...defaultCommercialArea, ...initialData }));
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
        const [agentsResponse, sellersResponse] = await Promise.all([getAgents(), getSellers()]);
        setAgents(agentsResponse);
        setSellers(sellersResponse);
      } catch {
        setError("Erro ao carregar agentes e vendedores.");
      }
    };
    fetchData();
  }, []);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>) => {
    const { name, value, type } = e.target;
    let formattedValue: any = value;

    if (type === "text" || type === "textarea") {
      formattedValue = value;
     } else if (name === "status") {
       formattedValue = value as PropertyStatus;
    } else if (name === "price" || name === "rentalValue" || name === "condominiumFee") {
      formattedValue = value ? parseFloat(value.replace(/\./g, "").replace(",", ".")) : 0;
    } else if (type === "number") {
      formattedValue = Number(value);
    } else if (type === "select-one") {
      if (name === "agentId" || name === "sellerId") {
        formattedValue = value ? parseInt(value, 10) : 0;
      } else {
        formattedValue = value === "true";
      }
    }

    setCommercialArea((prev) => ({
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
        await updateCommercialArea(commercialArea.propertyCode ?? '', commercialArea);
        setSuccessMessage("Área comercial atualizada com sucesso!");
      } else {
        const { id, propertyCode, createdAt, updatedAt, ...commercialAreaData } = commercialArea;
        await createCommercialArea(commercialAreaData);
        setSuccessMessage("Área comercial criada com sucesso!");
      }
      onSuccess();
    } catch {
      setError(`Erro ao ${isEditMode ? "atualizar" : "criar"}a área comercial.`);
    } finally {
      setLoading(false);
    }
  };

   const print = () => {
        navigate('/print/commercial_area');
      };


  return (
    <div className="form-container">
      <h2>{isEditMode ? 'Editar' : 'Cadastrar'} Área Comercial</h2>
      {error && <div className="error-message">{error}</div>}
      {successMessage && <div className="success-message">{successMessage}</div>}
      <div className="form-header"><button onClick={() => print()}>Imprimir ficha</button></div>
      <form onSubmit={handleSubmit}>
        <h3>Informações Gerais</h3>
        <FormField label="Preço" type="text" name="price" value={commercialArea.price.toString()} onChange={handleChange} />
        <FormField label="Rua" type="text" name="street" value={commercialArea.street} onChange={handleChange} />
        <FormField label="Quadra" type="text" name="block" value={commercialArea.block} onChange={handleChange} />
        <FormField label="Lote" type="text" name="lot" value={commercialArea.lot} onChange={handleChange} />
        <FormField label="Número" type="text" name="number" value={commercialArea.number} onChange={handleChange} />
        <FormField label="Complemento" type="text" name="complement" value={commercialArea.complement} onChange={handleChange} />
        <FormField label="Bairro" type="text" name="neighborhood" value={commercialArea.neighborhood} onChange={handleChange} />
        <FormField label="Cidade" type="text" name="city" value={commercialArea.city} onChange={handleChange} />
        <FormField label="Estado" type="text" name="state" value={commercialArea.state} onChange={handleChange} />
        <FormField label="Orientação" type="text" name="orientation" value={commercialArea.orientation} onChange={handleChange} />
        <SelectField label="Alugado" name="isRented" value={commercialArea.isRented ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <FormField label="Valor do Aluguel" type="number" name="rentalValue" value={commercialArea.rentalValue} onChange={handleChange} />
        <SelectField label="Ocupado" name="isInhabited" value={commercialArea.isInhabited ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Possui Muro"name="hasWall"value={commercialArea.hasWall ? 'Sim' : 'Não'}options={[{ id: 'Sim', name: 'Sim' }, { id: 'Não', name: 'Não' }, ]} onChange={handleChange} />
        <SelectField label="Possui Asfalto" name="hasAsphalt" value={commercialArea.hasAsphalt ? 'Sim' : 'Não'} options={[ { id: 'Sim', name: 'Sim' }, { id: 'Não', name: 'Não' }, ]} onChange={handleChange} />

        <h3>Áreas</h3>
        <FormField label="Área de terreno" type="number" name="totalArea" value={commercialArea.totalArea} onChange={handleChange} />

        <h3>Outras Informações</h3>
        <FormField label="Descrição" type="textarea" name="description" value={commercialArea.description || ''} onChange={handleChange} />
        <FormField label="Local das Chaves" type="text" name="placeOfKeys" value={commercialArea.placeOfKeys} onChange={handleChange} />
        <FormField label="Hora de Visita" type="text" name="visitingTime" value={commercialArea.visitingTime} onChange={handleChange} />

        <SelectField
          label="Status da Propriedade"
          name="status"
          value={commercialArea.status}
          options={Object.entries(PropertyStatus).map(([key, value]) => ({
            id: value,
            name: PropertyStatusDescription[value as PropertyStatus]
          }))}
          onChange={handleChange}
        />
        <SelectField
            label="Agente"
            name="agentId"
            value={commercialArea.agentId?.toString() || ''}
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
            value={commercialArea.sellerId?.toString() || ''}
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
            {loading ? 'Carregando...' : isEditMode ? 'Salvar Alterações' : 'Adicionar área comercial'}
        </button>
        <button type="button" className="cancel-button" onClick={onCancel}>
        Cancelar
        </button>
            </form>
       </div>
       )
    };


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

interface FormFieldProps {
  label: string;
  type: string;
  name: string;
  value: string | number;
  onChange: (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => void;
  required?: boolean;
}

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

export default CommercialAreaForm;