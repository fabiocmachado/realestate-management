import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { FarmDTO, PropertyStatusDescription, Agent, Seller, PropertyStatus, PropertyType, ConservationStatus, EnergyType } from "../../types/models";
import { getAgents } from "../../services/agentService";
import { getSellers } from "../../services/sellerService";
import { createFarm, updateFarm, getFarmByCode } from "../../services/farmService";
import { formatPrice } from "../../components/shared/shared";
import "../../styles/propertiesForms.css";

interface FarmFormProps {
  initialData?: FarmDTO;
  onSuccess: () => void;
  onCancel?: () => void;
}

const defaultFarm: FarmDTO = {
  name: '',
  typeOfSoil: '',
  predominantPasture: '',
  otherPastures: '',
  pastureConservation: ConservationStatus.GOOD,
  hasSmoothWireFence: false,
  topography: '',
  hasRocks: false,
  rivers: '',
  dams: '',
  mainHouse: '',
  staffHouse: '',
  energy: EnergyType.NONE,
  hasOrchard: false,
  warehouse: '',
  accommodation: '',
  pens: '',
  hasLandingStrip: false,
  herdSupport: 0,
  distanceOfGyn: 0,
  distanceOfCity: 0,
  distanceDirtRoad: 0,
  pastures: 0,
  formedArea: 0,
  totalAreaRural: 0,
  legalReserveArea: 0,
  isRented: false,
  rentalValue: 0,
  visitingTime: '',
  id: 0,
  propertyCode: '',
  propertyType: PropertyType.RURAL,
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
  propertyCategory: 'FARM',
  usableArea: 0,
  privateArea: 0,
  totalArea: 0,
  agentId: 0,
  sellerId: 0,
};

const FarmForm: React.FC<FarmFormProps> = ({ initialData, onSuccess, onCancel }) => {
  const [farm, setFarm] = useState<FarmDTO>(() => {
    return initialData || defaultFarm;
  });

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
      } catch (error) {
        setError("Erro ao carregar agentes e vendedores.");
      }
    };
    fetchData();
  }, []);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>) => {
    const { name, value, type } = e.target;
    let formattedValue: any = value;

    if (name === "price" || name === "rentalValue") {
      formattedValue = value ? parseFloat(value.replace(/\./g, "").replace(",", ".")) : 0;
    } else if (["mainHouse", "staffHouse", "warehouse", "accommodation", "pens", "hasSmoothWireFence", "hasRocks", "hasOrchard", "isRented"].includes(name)) {
      formattedValue = value === "true";
    } else if (["formedArea", "totalAreaRural", "legalReserveArea"].includes(name)) {
      formattedValue = Number(value) || 0;
    }

    setFarm(prev => ({
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
        await updateFarm(farm.propertyCode!, farm);
        setSuccessMessage("Fazenda atualizada com sucesso!");
      } else {
        const { id, propertyCode, createdAt, updatedAt, ...farmData } = farm;
        await createFarm(farmData);
        setSuccessMessage("Fazenda criada com sucesso!");
      }
      onSuccess();
    } catch (error) {
      setError(`Erro ao ${isEditMode ? "atualizar" : "criar"} a fazenda.`);
    } finally {
      setLoading(false);
    }
  };

const print = () => {
      navigate('/print/farm');
    };
  return (
    <div className="form-container">
      <h2>{isEditMode ? 'Editar' : 'Cadastrar'} Fazenda</h2>
      {error && <div className="error-message">{error}</div>}
      {successMessage && <div className="success-message">{successMessage}</div>}
      <div className="form-header"><button onClick={() => print()}>Imprimir ficha</button></div>
      <form onSubmit={handleSubmit}>
        <h3>Informações Gerais</h3>
        <FormField label="Nome" type="text" name="name" value={farm.name.toString()} onChange={handleChange} />
        <FormField label="Preço" type="text" name="price" value={farm.price.toString()} onChange={handleChange} />
        <FormField label="Rua" type="text" name="street" value={farm.street} onChange={handleChange} />
        <FormField label="Quadra" type="text" name="block" value={farm.block} onChange={handleChange} />
        <FormField label="Lote" type="text" name="lot" value={farm.lot} onChange={handleChange} />
        <FormField label="Número" type="text" name="number" value={farm.number} onChange={handleChange} />
        <FormField label="Complemento" type="text" name="complement" value={farm.complement} onChange={handleChange} />
        <FormField label="Bairro" type="text" name="neighborhood" value={farm.neighborhood} onChange={handleChange} />
        <FormField label="Cidade" type="text" name="city" value={farm.city} onChange={handleChange} />
        <FormField label="Estado" type="text" name="state" value={farm.state} onChange={handleChange} />
        <SelectField label="Arrendada" name="isRented" value={farm.isRented ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <FormField label="Valor do arrendamento" type="number" name="rentalValue" value={farm.rentalValue ?? 0} onChange={handleChange} />

        <h3>Áreas</h3>
        <FormField
          label="Área total (alqueires)"
          type="number"
          name="totalAreaRural"
          value={farm.totalAreaRural}
          onChange={handleChange}
        />
        <FormField
          label="Área formada (alqueires)"
          type="number"
          name="formedArea"
          value={farm.formedArea}
          onChange={handleChange}
        />
        <FormField
          label="Área de reserva legal (alqueires)"
          type="number"
          name="legalReserveArea"
          value={farm.legalReserveArea}
          onChange={handleChange}
        />

        <h3>Infraestrutura</h3>
        <SelectField label="Casa Principal" name="mainHouse" value={farm.mainHouse ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Casa para Funcionários" name="staffHouse" value={farm.staffHouse ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Galpão" name="warehouse" value={farm.warehouse ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Alojamento" name="accommodation" value={farm.accommodation ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <SelectField label="Curral" name="pens" value={farm.pens ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <FormField label="Energia" type="text" name="energy" value={farm.energy ?? 0} onChange={handleChange} />

        <h2>Pastagens e Conservação</h2>
        <FormField label="Capacidade de Rebanho" type="text" name="herdSupport" value={farm.herdSupport ?? 0} onChange={handleChange} />
        <FormField label="Quantidade de pastos" type="text" name="pastures" value={farm.pastures ?? 0} onChange={handleChange} />
        <FormField label="Tipo de Solo" type="text" name="typeOfSoil" value={farm.typeOfSoil ?? 0} onChange={handleChange} />
        <FormField label="Pastagem Predominante" type="text" name="predominantPasture" value={farm.predominantPasture ?? 0} onChange={handleChange} />
        <FormField label="Outras Pastagens" type="text" name="otherPastures" value={farm.otherPastures ?? 0} onChange={handleChange} />
        <FormField label="Conservação das Pastagens" type="text" name="pastureConservation" value={farm.pastureConservation ?? 0} onChange={handleChange} />
        <SelectField label="Cercas de Arame Liso" name="hasSmoothWireFence" value={farm.hasSmoothWireFence ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />

        <h2>Distâncias</h2>
        <FormField label="Distância até Goiânia" type="text" name="distanceOfGyn" value={farm.distanceOfGyn ?? 0} onChange={handleChange} />
        <FormField label="Distância até cidade" type="text" name="distanceOfCity" value={farm.distanceOfCity ?? 0} onChange={handleChange} />
        <FormField label="Distância de Estrada de Terra" type="text" name="distanceDirtRoad" value={farm.distanceDirtRoad ?? 0} onChange={handleChange} />

        <h2>Recursos Naturais</h2>
        <FormField label="Topografia" type="text" name="topography" value={farm.topography ?? 0} onChange={handleChange} />
        <SelectField label="Pedras" name="hasRocks" value={farm.hasRocks ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <FormField label="Rios" type="text" name="rivers" value={farm.rivers ?? 0} onChange={handleChange} />
        <FormField label="Represas" type="text" name="dams" value={farm.dams ?? 0} onChange={handleChange} />
        <SelectField label="Pomar" name="hasOrchard" value={farm.hasOrchard ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />

        <h2>Descrição</h2>
        <FormField label="Descrição" type="textarea" name="description" value={farm.description || ''} onChange={handleChange} />

        <h2>Outras Informações</h2>
        <FormField label="Local das chaves" type="text" name="placeOfKeys" value={farm.placeOfKeys} onChange={handleChange} />
        <FormField label="Hora de Visita" type="text" name="visitingTime" value={farm.visitingTime} onChange={handleChange} />
        <SelectField
         label="Status da Propriedade"
         name="status"
         value={farm.status}
         options={Object.entries(PropertyStatus).map(([key, value]) => ({
           id: value,
           name: PropertyStatusDescription[value] || key
         }))}
         onChange={handleChange}
        />
        <SelectField
          label="Agente"
          name="agentId"
          value={farm.agentId?.toString() || ''}
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
          value={farm.sellerId?.toString() || ''}
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
          {loading ? 'Carregando...' : isEditMode ? 'Salvar Alterações' : 'Adicionar fazenda'}
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

export default FarmForm;