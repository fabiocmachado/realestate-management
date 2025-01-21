import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { TownhouseDTO, PropertyStatusDescription, Agent, Seller, PropertyStatus, PropertyType } from "../../types/models";
import { getAgents } from "../../services/agentService";
import { getSellers } from "../../services/sellerService";
import { createTownhouse, updateTownhouse, getTownhouseByCode } from "../../services/townhouseService";
import "../../styles/propertiesForms.css";

const formatPrice = (value: string) => {
  let number = value.replace(/[^\d,]/g, '');
  const [integer, decimal] = number.split(',');
  const formattedInteger = integer ? integer.replace(/\B(?=(\d{3})+(?!\d))/g, '.') : '';
  const formattedDecimal = decimal ? decimal.substring(0, 2) : '';
  return formattedDecimal ? `${formattedInteger},${formattedDecimal}` : formattedInteger;
};

interface TownhouseFormProps {
  initialData?: TownhouseDTO;
  onSuccess: () => void;
  onCancel?: () => void;
}

const defaultTownhouse: TownhouseDTO = {
  suites: 0,
  livingRoom: 0,
  offices: 0,
  hasLaundry: false,
  hasSauna: false,
  hasCabinets: false,
  hasElectronicGate: false,
  hasSurveillanceCameras: false,
  hasGarden: false,
  hasAirConditioning: false,
  hasKennel: false,
  floorType: '',
  isInhabited: false,
  isRented: false,
  hasBalcony: false,
  yearsOfConstruction: 0,
  hasYard: false,
  hasSwimmingPool: false,
  hasBarbecueGrill: false,
  toilet: 0,
  hasSolarEnergy: false,
  condominiumFee: 0,
  nameOfGatedCommunity: '',
  hasPantry: false,
  hasGym: false,
  bedrooms: 0,
  bathrooms: 0,
  garageSpaces: 0,
  hasKitchen: false,
  rentalValue: 0,
  hasGourmetBalcony: false,
  hasElectronicDoorman: false,
  hasEmployeeRoom: false,
  hasEmployeeBathroom: false,
  hasIntercom: false,
  hasGaragesInRow: false,
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
  visitingTime: '',
  status: PropertyStatus.AVAILABLE,
  createdAt: '',
  updatedAt: '',
  propertyCategory: 'TOWNHOUSE',
  usableArea: 0,
  privateArea: 0,
  totalArea: 0,
  agentId: 0,
  sellerId: 0,
};

const TownhouseForm: React.FC<TownhouseFormProps> = ({ initialData, onSuccess, onCancel }) => {
  const [townhouse, setTownhouse] = useState<TownhouseDTO>(() => ({ ...defaultTownhouse, ...initialData }));
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

      setTownhouse((prev) => ({
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
        await updateTownhouse(townhouse.propertyCode ?? '', townhouse);
        setSuccessMessage("Sobrado atualizado com sucesso!");
      } else {
        const { id, propertyCode, createdAt, updatedAt, ...townhouseData } = townhouse;
        await createTownhouse(townhouseData);
        setSuccessMessage("Sobrado criado com sucesso!");
      }
      onSuccess();
    } catch {
      setError(`Erro ao ${isEditMode ? "atualizar" : "criar"} o sobrado.`);
    } finally {
      setLoading(false);
    }
  };

  const print = () => {
        navigate('/print/townhouse');
      };


  return (
    <div className="form-container">
      <h2>{isEditMode ? 'Editar' : 'Cadastrar'} Sobrado</h2>
      {error && <div className="error-message">{error}</div>}
      {successMessage && <div className="success-message">{successMessage}</div>}
      <div className="form-header"><button onClick={() => print()}>Imprimir ficha</button></div>
      <form onSubmit={handleSubmit}>
        <h3>Informações Gerais</h3>
        <FormField label="Rua" type="text" name="street" value={townhouse.street} onChange={handleChange} />
        <FormField label="Quadra" type="text" name="block" value={townhouse.block} onChange={handleChange} />
        <FormField label="Lote" type="text" name="lot" value={townhouse.lot} onChange={handleChange} />
        <FormField label="Número" type="text" name="number" value={townhouse.number} onChange={handleChange} />
        <FormField label="Complemento" type="text" name="complement" value={townhouse.complement} onChange={handleChange} />
        <FormField label="Bairro" type="text" name="neighborhood" value={townhouse.neighborhood} onChange={handleChange} />
        <FormField label="Cidade" type="text" name="city" value={townhouse.city} onChange={handleChange} />
        <FormField label="Estado" type="text" name="state" value={townhouse.state} onChange={handleChange} />
        <FormField label="Preço" type="text" name="price" value={townhouse.price.toString()} onChange={handleChange} />
        <FormField label="Taxa de Condomínio" type="number" name="condominiumFee" value={townhouse.condominiumFee} onChange={handleChange} />
        <FormField label="Orientação" type="text" name="orientation" value={townhouse.orientation} onChange={handleChange} />
        <SelectField label="Ocupado" name="isInhabited" value={townhouse.isInhabited ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Alugado" name="isRented" value={townhouse.isRented ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <FormField label="Valor do aluguel" type="number" name="rentalValue" value={townhouse.rentalValue ?? 0} onChange={handleChange} />
        <h3>Áreas</h3>
        <FormField label="Área de terreno" type="text" name="totalArea" value={townhouse.totalArea ?? 0} onChange={handleChange} />
        <FormField label="Área construída" type="text" name="usableArea" value={townhouse.usableArea ?? 0} onChange={handleChange} />
        <h3>Divisões Internas</h3>
        <FormField label="Quartos" type="number" name="bedrooms" value={townhouse.bedrooms ?? 0} onChange={handleChange} />
        <FormField label="Suítes" type="number" name="suites" value={townhouse.suites ?? 0} onChange={handleChange} />
        <FormField label="Salas" type="number" name="livingRoom" value={townhouse.livingRoom ?? 0} onChange={handleChange} />
        <FormField label="Banheiro social" type="number" name="bathrooms" value={townhouse.bathrooms ?? 0} onChange={handleChange} />
        <FormField label="Lavabo" type="number" name="toilet" value={townhouse.toilet ?? 0} onChange={handleChange} />
        <FormField label="Escritórios" type="number" name="offices" value={townhouse.offices ?? 0} onChange={handleChange} />
        <SelectField label="Garagens em gaveta" name="hasGaragesInRow" value={townhouse.hasGaragesInRow ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Cozinha" name="hasKitchen" value={townhouse.hasKitchen ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Despensa" name="hasPantry" value={townhouse.hasPantry ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Lavanderia" name="hasLaundry" value={townhouse.hasLaundry ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Quarto para funcionário" name="hasEmployeeRoom" value={townhouse.hasEmployeeRoom ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Banheiro para funcionário" name="hasEmployeeBathroom" value={townhouse.hasEmployeeBathroom ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <h3>Comodidades</h3>
        <FormField label="Tipo de piso" type="text" name="floorType" value={townhouse.floorType} onChange={handleChange} />
        <SelectField label="Armários" name="hasCabinets" value={townhouse.hasCabinets ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Interfone" name="hasIntercom" value={townhouse.hasIntercom ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Câmeras de Vigilância" name="hasSurveillanceCameras" value={townhouse.hasSurveillanceCameras ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Varanda Gourmet" name="hasGourmetBalcony" value={townhouse.hasGourmetBalcony ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Ar-condicionado" name="hasAirConditioning" value={townhouse.hasAirConditioning ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Churrasqueira" name="hasBarbecueGrill" value={townhouse.hasBarbecueGrill ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Piscina" name="hasSwimmingPool" value={townhouse.hasSwimmingPool ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Sauna" name="hasSauna" value={townhouse.hasSauna ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Portão Eletrônico" name="hasElectronicGate" value={townhouse.hasElectronicGate ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Portaria Eletrônica" name="hasElectronicDoorman" value={townhouse.hasElectronicDoorman ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />

        <h2>Descrição</h2>
        <FormField label="Descrição" type="textarea" name="description" value={townhouse.description || ''} onChange={handleChange} />
        <h2>Outras Informações</h2>
        <FormField label="Local das chaves" type="text" name="placeOfKeys" value={townhouse.placeOfKeys} onChange={handleChange} />
        <FormField label="Hora de Visita" type="text" name="visitingTime" value={townhouse.visitingTime} onChange={handleChange} />
        <SelectField
         label="Status da Propriedade"
         name="status"
         value={townhouse.status}
         options={Object.entries(PropertyStatus).map(([key, value]) => ({
           id: value,
           name: PropertyStatusDescription[value] || key
         }))}
         onChange={handleChange}
        />
        <SelectField
          label="Agente"
          name="agentId"
          value={townhouse.agentId?.toString() || ''}
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
          label="Proprietário"
          name="sellerId"
          value={townhouse.sellerId?.toString() || ''}
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
          {loading ? 'Carregando...' : isEditMode ? 'Salvar Alterações' : 'Adicionar sobrado'}
        </button>
        <button type="button" className="cancel-button" onClick={onCancel}>
          Cancelar
        </button>
      </form>
    </div>
  );
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
export default TownhouseForm;