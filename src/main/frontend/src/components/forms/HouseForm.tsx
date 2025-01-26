import React, { useEffect, useState } from "react";
import { HouseDTO, PropertyStatusDescription, Agent, Seller, PropertyStatus, PropertyType } from "../../types/models";
import { getAgents } from "../../services/agentService";
import { getSellers } from "../../services/sellerService";
import { createHouse, updateHouse, getHouseByCode } from "../../services/houseService";
import { useNavigate } from "react-router-dom";

const formatPrice = (value: string) => {
  let number = value.replace(/[^\d,]/g, '');
  const [integer, decimal] = number.split(',');
  const formattedInteger = integer ? integer.replace(/\B(?=(\d{3})+(?!\d))/g, '.') : '';
  const formattedDecimal = decimal ? decimal.substring(0, 2) : '';
  return formattedDecimal ? `${formattedInteger},${formattedDecimal}` : formattedInteger;
};

interface HouseFormProps {
  initialData?: HouseDTO;
  onSuccess: () => void;
  onCancel?: () => void;
}

const defaultHouse: HouseDTO = {
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
  propertyCategory: 'HOUSE',
  usableArea: 0,
  privateArea: 0,
  totalArea: 0,
  agentId: 0,
  sellerId: 0,
};

const HouseForm: React.FC<HouseFormProps> = ({ initialData, onSuccess, onCancel }) => {
  const [house, setHouse] = useState<HouseDTO>(() => ({ ...defaultHouse, ...initialData }));
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
      } else if (name === "price") {
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

      setHouse((prev) => ({
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
        await updateHouse(house.propertyCode ?? '', house);
        setSuccessMessage("Casa atualizada com sucesso!");
      } else {
        const { id, propertyCode, createdAt, updatedAt, ...houseData } = house;
        await createHouse(houseData);
        setSuccessMessage("Casa criada com sucesso!");
      }
      onSuccess();
    } catch {
      setError(`Erro ao ${isEditMode ? "atualizar" : "criar"} a casa.`);
    } finally {
      setLoading(false);
    }
  };

  const print = () => {
        navigate('/print/house');
      };

  return (
    <div className="form-container">
      <h2>{isEditMode ? 'Editar' : 'Cadastrar'} Casa</h2>
      {error && <div className="error-message">{error}</div>}
      {successMessage && <div className="success-message">{successMessage}</div>}
      <div className="form-header"><button onClick={() => print()}>Imprimir ficha</button></div>
      <form onSubmit={handleSubmit}>
      <div className="form-section">
        <h3>Informações Gerais</h3>
         <div className="form-group">
        <FormField label="Rua" type="text" name="street" value={house.street} onChange={handleChange} />
        <FormField label="Quadra" type="text" name="block" value={house.block} onChange={handleChange} />
        <FormField label="Lote" type="text" name="lot" value={house.lot} onChange={handleChange} />
        <FormField label="Número" type="text" name="number" value={house.number} onChange={handleChange} />
        <FormField label="Complemento" type="text" name="complement" value={house.complement} onChange={handleChange} />
        <FormField label="Bairro" type="text" name="neighborhood" value={house.neighborhood} onChange={handleChange} />
        <FormField label="Cidade" type="text" name="city" value={house.city} onChange={handleChange} />
        <FormField label="Estado" type="text" name="state" value={house.state} onChange={handleChange} />
        </div>
        <div className="form-group">
        <FormField label="Preço" type="text" name="price" value={house.price.toString()} onChange={handleChange} />
        <FormField label="Taxa de Condomínio" type="number" name="condominiumFee" value={house.condominiumFee} onChange={handleChange} />
        <FormField label="Orientação" type="text" name="orientation" value={house.orientation} onChange={handleChange} />
        <SelectField label="Ocupado" name="isInhabited" value={house.isInhabited ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Alugado" name="isRented" value={house.isRented ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <FormField label="Valor do aluguel" type="number" name="rentalValue" value={house.rentalValue ?? 0} onChange={handleChange} />
        </div>
        </div>
        <div className="form-section">
        <h3>Áreas</h3>
        <FormField label="Área de terreno" type="text" name="totalArea" value={house.totalArea ?? 0} onChange={handleChange} />
        <FormField label="Área construída" type="text" name="usableArea" value={house.usableArea ?? 0} onChange={handleChange} />
        </div>
        <div className="form-section">
        <h3>Divisões Internas</h3>
        <FormField label="Quartos" type="number" name="bedrooms" value={house.bedrooms ?? 0} onChange={handleChange} />
        <FormField label="Suítes" type="number" name="suites" value={house.suites ?? 0} onChange={handleChange} />
        <FormField label="Salas" type="number" name="livingRoom" value={house.livingRoom ?? 0} onChange={handleChange} />
        <FormField label="Banheiro social" type="number" name="bathrooms" value={house.bathrooms ?? 0} onChange={handleChange} />
        <FormField label="Lavabo" type="number" name="toilet" value={house.toilet ?? 0} onChange={handleChange} />
        <FormField label="Escritórios" type="number" name="offices" value={house.offices ?? 0} onChange={handleChange} />
        <SelectField label="Cozinha" name="hasKitchen" value={house.hasKitchen ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Despensa" name="hasPantry" value={house.hasPantry ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Lavanderia" name="hasLaundry" value={house.hasLaundry ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Quarto para funcionário" name="hasEmployeeRoom" value={house.hasEmployeeRoom ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Banheiro para funcionário" name="hasEmployeeBathroom" value={house.hasEmployeeBathroom ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        </div>
        <div className="form-section">
        <h3>Comodidades</h3>
        <FormField label="Tipo de piso" type="text" name="floorType" value={house.floorType} onChange={handleChange} />
        <FormField label="Vagas de garagems" type="number" name="garageSpaces" value={house.garageSpaces?? 0} onChange={handleChange} />
        <SelectField label="Garagens em gaveta" name="hasGaragesInRow" value={house.hasGaragesInRow ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Armários" name="hasCabinets" value={house.hasCabinets ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Interfone" name="hasIntercom" value={house.hasIntercom ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Câmeras de Vigilância" name="hasSurveillanceCameras" value={house.hasSurveillanceCameras ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Varanda Gourmet" name="hasGourmetBalcony" value={house.hasGourmetBalcony ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Ar-condicionado" name="hasAirConditioning" value={house.hasAirConditioning ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Churrasqueira" name="hasBarbecueGrill" value={house.hasBarbecueGrill ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Piscina" name="hasSwimmingPool" value={house.hasSwimmingPool ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Sauna" name="hasSauna" value={house.hasSauna ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Portão Eletrônico" name="hasElectronicGate" value={house.hasElectronicGate ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Portaria Eletrônica" name="hasElectronicDoorman" value={house.hasElectronicDoorman ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        </div>
        <div className="form-section">
        <h2>Descrição</h2>
        <FormField label="Descrição" type="textarea" name="description" value={house.description || ''} onChange={handleChange} />
        </div>
        <div className="form-section">
        <h2>Outras Informações</h2>
        <FormField label="Local das chaves" type="text" name="placeOfKeys" value={house.placeOfKeys} onChange={handleChange} />
        <FormField label="Hora de Visita" type="text" name="visitingTime" value={house.visitingTime} onChange={handleChange} />
        <SelectField
             label="Status da Propriedade"
             name="status"
             value={house.status}
             options={Object.entries(PropertyStatus).map(([key, value]) => ({
               id: value,
               name: PropertyStatusDescription[value] || key
             }))}
             onChange={handleChange}
        />
        <SelectField
          label="Agente"
          name="agentId"
          value={house.agentId?.toString() || ''}
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
          value={house.sellerId?.toString() || ''}
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
       </div>
        <button type="submit" disabled={loading}>
          {loading ? 'Carregando...' : isEditMode ? 'Salvar Alterações' : 'Adicionar casa'}
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
export default HouseForm;