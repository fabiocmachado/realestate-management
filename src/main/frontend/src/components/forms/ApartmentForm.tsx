import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { ApartmentDTO, PropertyStatusDescription, Agent, Seller, PropertyStatus, PropertyType } from "../../types/models";
import { getAgents } from "../../services/agentService";
import { getSellers } from "../../services/sellerService";
import { createApartment, updateApartment, getApartmentByCode } from "../../services/apartmentService";
import { ApartmentBlankForm } from '../blankForms/ApartmentBlankForm';
import "../../styles/propertiesForms.css";

const formatPrice = (value: string) => {
  let number = value.replace(/[^\d,]/g, '');
  const [integer, decimal] = number.split(',');
  const formattedInteger = integer ? integer.replace(/\B(?=(\d{3})+(?!\d))/g, '.') : '';
  const formattedDecimal = decimal ? decimal.substring(0, 2) : '';
  return formattedDecimal ? `${formattedInteger},${formattedDecimal}` : formattedInteger;
};

interface ApartmentFormProps {
  initialData?: ApartmentDTO;
  onSuccess: () => void;
  onCancel?: () => void;
}

const defaultApartment: ApartmentDTO = {
  apartmentNumber: '',
  numberOfFloors: 0,
  livingRoom: 0,
  condominiumFee: 0,
  offices: 0,
  nameOfBuilding: '',
  hasPantry: false,
  toilet: 0,
  hasBarbecue: false,
  hasSwimmingPool: false,
  yearsOfConstruction: 0,
  hasBalcony: false,
  isRented: false,
  isInhabited: false,
  floorType: '',
  hasAirConditioning: false,
  hasSurveillanceCameras: false,
  hasCabinets: false,
  hasLaundry: false,
  hasSauna: false,
  suites: 0,
  hasBars: false,
  hasEmployeeRoom: false,
  hasEmployeeBathroom: false,
  hasGaragesInRow: false,
  numberOfBlocks: 0,
  totalOfApartments: 0,
  hasPartyHall: false,
  hasGameRoom: false,
  hasPlayground: false,
  hasToyArea: false,
  hasSportsCourt: false,
  hasElectronicGate: false,
  hasElectronicDoorman: false,
  hasIntercom: false,
  hasGourmetBalcony: false,
  elevator: 0,
  hasCompartment: false,
  visitingTime: '',
  hasPrivateSwimmingPool: false,
  hasExclusiveSauna: false,
  hasMezzanine: false,
  hasGym: false,
  bedrooms: 0,
  bathrooms: 0,
  garageSpaces: 0,
  hasKitchen: false,
  rentalValue: 0,
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
  propertyCategory: 'APARTMENT',
  usableArea: 0,
  privateArea: 0,
  totalArea: 0,
  agentId: 0,
  sellerId: 0,
};

const ApartmentForm: React.FC<ApartmentFormProps> = ({ initialData, onSuccess, onCancel }) => {
  const [apartment, setApartment] = useState<ApartmentDTO>(() => ({ ...defaultApartment, ...initialData }));
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

      setApartment((prev) => ({
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
        await updateApartment(apartment.propertyCode ?? '', apartment);
        setSuccessMessage("Apartamento atualizado com sucesso!");
      } else {
        const { id, propertyCode, createdAt, updatedAt, ...apartmentData } = apartment;
        await createApartment(apartmentData);
        setSuccessMessage("Apartamento criado com sucesso!");
      }
      onSuccess();
    } catch {
      setError(`Erro ao ${isEditMode ? "atualizar" : "criar"} o apartamento.`);
    } finally {
      setLoading(false);
    }
  };

  const print = () => {
      navigate('/print/apartment');
    };

  return (
    <div className="form-container">
      <h2>{isEditMode ? 'Editar' : 'Cadastrar'} Apartamento</h2>
      {error && <div className="error-message">{error}</div>}
      {successMessage && <div className="success-message">{successMessage}</div>}
      <div className="form-header"><button onClick={() => print()}>Imprimir ficha</button></div>
      <form onSubmit={handleSubmit}>
       <div className="form-section">
        <h3>Informações Gerais</h3>
        <div className="form-group">
        <FormField label="Preço" type="text" name="price" value={apartment.price.toString()} onChange={handleChange} />
        <FormField label="Nome do Prédio" type="text" name="nameOfBuilding" value={apartment.nameOfBuilding} onChange={handleChange} />
        <FormField label="Número do Apartamento" type="text" name="apartmentNumber" value={apartment.apartmentNumber} onChange={handleChange} />
        <FormField label="Taxa de Condomínio" type="number" name="condominiumFee" value={apartment.condominiumFee} onChange={handleChange} />
        </div>
        <div className="form-group">
        <FormField label="Rua" type="text" name="street" value={apartment.street} onChange={handleChange} />
        <FormField label="Quadra" type="text" name="block" value={apartment.block} onChange={handleChange} />
        <FormField label="Lote" type="text" name="lot" value={apartment.lot} onChange={handleChange} />
        <FormField label="Número" type="text" name="number" value={apartment.number} onChange={handleChange} />
        <FormField label="Complemento" type="text" name="complement" value={apartment.complement} onChange={handleChange} />
        <FormField label="Bairro" type="text" name="neighborhood" value={apartment.neighborhood} onChange={handleChange} />
        <FormField label="Cidade" type="text" name="city" value={apartment.city} onChange={handleChange} />
        <FormField label="Estado" type="text" name="state" value={apartment.state} onChange={handleChange} />
        <FormField label="Orientação" type="text" name="orientation" value={apartment.orientation} onChange={handleChange} />
        <SelectField label="Ocupado" name="isInhabited" value={apartment.isInhabited ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Alugado" name="isRented" value={apartment.isRented ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <FormField label="Valor do aluguel" type="number" name="rentalValue" value={apartment.rentalValue ?? 0} onChange={handleChange} />
        </div>
        </div>
        <div className="form-section">
        <h3>Divisões Internas</h3>
        <FormField label="Quartos" type="number" name="bedrooms" value={apartment.bedrooms ?? 0} onChange={handleChange} />
        <FormField label="Suítes" type="number" name="suites" value={apartment.suites ?? 0} onChange={handleChange} />
        <FormField label="Salas" type="number" name="livingRoom" value={apartment.livingRoom ?? 0} onChange={handleChange} />
        <FormField label="Banheiro social" type="number" name="bathrooms" value={apartment.bathrooms ?? 0} onChange={handleChange} />
        <FormField label="Lavabo" type="number" name="toilet" value={apartment.toilet ?? 0} onChange={handleChange} />
        <FormField label="Escritórios" type="number" name="offices" value={apartment.offices ?? 0} onChange={handleChange} />
        <SelectField label="Garagens em gaveta" name="hasGaragesInRow" value={apartment.hasGaragesInRow ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Cozinha" name="hasKitchen" value={apartment.hasKitchen ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Despensa" name="hasPantry" value={apartment.hasPantry ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Sacada" name="hasBalcony" value={apartment.hasBalcony ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Lavanderia" name="hasLaundry" value={apartment.hasLaundry ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Quarto para funcionário" name="hasEmployeeRoom" value={apartment.hasEmployeeRoom ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Banheiro para funcionário" name="hasEmployeeBathroom" value={apartment.hasEmployeeBathroom ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        </div>
        <div className="form-section">
        <h3>Comodidades</h3>
        <FormField label="Tipo de piso" type="text" name="floorType" value={apartment.floorType} onChange={handleChange} />
        <SelectField label="Escaninho" name="hasCompartment" value={apartment.hasCompartment ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Armários" name="hasCabinets" value={apartment.hasCabinets ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Interfone" name="hasIntercom" value={apartment.hasIntercom ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Câmeras de Vigilância" name="hasSurveillanceCameras" value={apartment.hasSurveillanceCameras ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Rede de segurança nas janelas" name="hasBars" value={apartment.hasBars ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Varanda Gourmet" name="hasGourmetBalcony" value={apartment.hasGourmetBalcony ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Ar-condicionado" name="hasAirConditioning" value={apartment.hasAirConditioning ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Sauna Exclusiva" name="hasExclusiveSauna" value={apartment.hasExclusiveSauna ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Piscina Exclusiva" name="hasPrivateSwimmingPool" value={apartment.hasPrivateSwimmingPool ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <h3>Áreas</h3>
        <FormField label="Área total" type="text" name="totalArea" value={apartment.totalArea ?? 0} onChange={handleChange} />
        <FormField label="Área Privativa" type="text" name="privateArea" value={apartment.privateArea ?? 0} onChange={handleChange} />
        <FormField label="Área útil" type="text" name="usableArea" value={apartment.usableArea ?? 0} onChange={handleChange} />
        </div>
        <div className="form-section">
        <h3>Infraestrutura do Condomínio</h3>
        <SelectField label="Salão de Festas" name="hasPartyHall" value={apartment.hasPartyHall ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Salão de Jogos" name="hasGameRoom" value={apartment.hasGameRoom ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Playground" name="hasPlayground" value={apartment.hasPlayground ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Brinquedoteca" name="hasToyArea" value={apartment.hasToyArea ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Quadra de Esportes" name="hasSportsCourt" value={apartment.hasSportsCourt ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Piscina" name="hasSwimmingPool" value={apartment.hasSwimmingPool ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Academia" name="hasGym" value={apartment.hasGym ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Sauna" name="hasSauna" value={apartment.hasSauna ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Churrasqueira" name="hasBarbecue" value={apartment.hasBarbecue ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Mezanino" name="hasMezzanine" value={apartment.hasMezzanine ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Portão Eletrônico" name="hasElectronicGate" value={apartment.hasElectronicGate ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Portaria Eletrônica" name="hasElectronicDoorman" value={apartment.hasElectronicDoorman ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <FormField label="Elevador" type="number" name="elevator" value={apartment.elevator ?? 0} onChange={handleChange} />
        <FormField label="Blocos" type="number" name="numberOfBlocks" value={apartment.numberOfBlocks ?? 0} onChange={handleChange} />
        <FormField label="Total de Apartamentos" type="number" name="totalOfApartments" value={apartment.totalOfApartments ?? 0} onChange={handleChange} />
        <FormField label="Total de Andares" type="number" name="numberOfFloors" value={apartment.numberOfFloors ?? 0} onChange={handleChange} />
        <FormField label="Anos de construção" type="number" name="yearsOfConstruction" value={apartment.yearsOfConstruction ?? 0} onChange={handleChange} />
        </div>
        <div className="form-section">
        <h2>Descrição</h2>
        <FormField label="Descrição" type="textarea" name="description" value={apartment.description || ''} onChange={handleChange} />
        </div>
        <div className="form-section">
        <h2>Outras Informações</h2>
        <FormField label="Local das chaves" type="text" name="placeOfKeys" value={apartment.placeOfKeys} onChange={handleChange} />
        <FormField label="Hora de Visita" type="text" name="visitingTime" value={apartment.visitingTime} onChange={handleChange} />
        <SelectField
         label="Status da Propriedade"
         name="status"
         value={apartment.status}
         options={Object.entries(PropertyStatus).map(([key, value]) => ({
           id: value,
           name: PropertyStatusDescription[value] || key
         }))}
         onChange={handleChange}
        />
        <SelectField
          label="Agente"
          name="agentId"
          value={apartment.agentId?.toString() || ''}
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
          value={apartment.sellerId?.toString() || ''}
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
          {loading ? 'Carregando...' : isEditMode ? 'Salvar Alterações' : 'Cadastrar Apartamento'}
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

export default ApartmentForm;