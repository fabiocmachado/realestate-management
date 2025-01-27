import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { CountryHouseDTO, PropertyStatusDescription, Agent, Seller, PropertyStatus, PropertyType, ConservationStatus, EnergyType } from "../../types/models";
import { getAgents } from "../../services/agentService";
import { getSellers } from "../../services/sellerService";
import { createCountryHouse, updateCountryHouse } from "../../services/countryHouseService";

interface CountryHouseFormProps {
  initialData?: CountryHouseDTO;
  onSuccess: () => void;
  onCancel?: () => void;
}

const defaultCountryHouse: CountryHouseDTO = {
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
  propertyCategory: 'COUNTRY_HOUSE',
  usableArea: 0,
  privateArea: 0,
  totalArea: 0,
  agentId: 0,
  sellerId: 0,
};

const CountryHouseForm: React.FC<CountryHouseFormProps> = ({ initialData, onSuccess, onCancel }) => {
  const [countryHouse, setCountryHouse] = useState<CountryHouseDTO>(() => {
    return initialData || defaultCountryHouse;
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
      const { name, value } = e.target;
      let formattedValue: any = value;

      if (name === "price" || name === "rentalValue") {
        formattedValue = value ? parseFloat(value.replace(/\./g, "").replace(",", ".")) : 0;
      } else if (["hasSmoothWireFence", "hasRocks", "hasOrchard", "isRented"].includes(name)) {
        formattedValue = value === "true";
      } else if (["formedArea", "totalAreaRural", "legalReserveArea", "herdSupport", "pastures", "distanceOfGyn", "distanceOfCity", "distanceDirtRoad"].includes(name)) {
        formattedValue = Number(value) || 0;
      } else {
        formattedValue = value;
      }

      setCountryHouse(prev => ({
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
        await updateCountryHouse(countryHouse.propertyCode!, countryHouse);
        setSuccessMessage("Chácara atualizada com sucesso!");
      } else {
        const { id, propertyCode, createdAt, updatedAt, ...countryHouseData } = countryHouse;
        await createCountryHouse(countryHouseData);
        setSuccessMessage("Chácara criada com sucesso!");
      }
      onSuccess();
    } catch (error) {
      setError(`Erro ao ${isEditMode ? "atualizar" : "criar"} a Chácara.`);
    } finally {
      setLoading(false);
    }
  };

  const print = () => {
        navigate('/print/country_house');
      };


  return (
    <div className="form-container">
      <h2>{isEditMode ? 'Editar' : 'Cadastrar'} Chácara</h2>
      {error && <div className="error-message">{error}</div>}
      {successMessage && <div className="success-message">{successMessage}</div>}
      <div className="form-header"><button onClick={() => print()}>Imprimir ficha</button></div>
      <form onSubmit={handleSubmit}>
        <h3>Informações Gerais</h3>
        <FormField label="Nome" type="text" name="name" value={countryHouse.name.toString()} onChange={handleChange} />
        <FormField label="Preço" type="text" name="price" value={countryHouse.price.toString()} onChange={handleChange} />
        <FormField label="Rua" type="text" name="street" value={countryHouse.street} onChange={handleChange} />
        <FormField label="Quadra" type="text" name="block" value={countryHouse.block} onChange={handleChange} />
        <FormField label="Lote" type="text" name="lot" value={countryHouse.lot} onChange={handleChange} />
        <FormField label="Número" type="text" name="number" value={countryHouse.number} onChange={handleChange} />
        <FormField label="Complemento" type="text" name="complement" value={countryHouse.complement} onChange={handleChange} />
        <FormField label="Bairro" type="text" name="neighborhood" value={countryHouse.neighborhood} onChange={handleChange} />
        <FormField label="Cidade" type="text" name="city" value={countryHouse.city} onChange={handleChange} />
        <FormField label="Estado" type="text" name="state" value={countryHouse.state} onChange={handleChange} />
        <SelectField label="Arrendada" name="isRented" value={countryHouse.isRented ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <FormField label="Valor do arrendamento" type="number" name="rentalValue" value={countryHouse.rentalValue ?? 0} onChange={handleChange} />

        <h3>Áreas</h3>
        <FormField
          label="Área total (alqueires)"
          type="number"
          name="totalAreaRural"
          value={countryHouse.totalAreaRural}
          onChange={handleChange}
        />
        <FormField
          label="Área formada (alqueires)"
          type="number"
          name="formedArea"
          value={countryHouse.formedArea}
          onChange={handleChange}
        />
        <FormField
          label="Área de reserva legal (alqueires)"
          type="number"
          name="legalReserveArea"
          value={countryHouse.legalReserveArea}
          onChange={handleChange}
        />

        <h3>Infraestrutura</h3>
        <FormField label="Casa Principal" type="text" name="mainHouse" value={countryHouse.mainHouse || ''} onChange={handleChange} />
        <FormField label="Casa para Funcionários" type="text" name="staffHouse" value={countryHouse.staffHouse || ''} onChange={handleChange} />
        <FormField label="Galpão" type="text" name="warehouse" value={countryHouse.warehouse || ''} onChange={handleChange} />
        <FormField label="Alojamento" type="text" name="accommodation" value={countryHouse.accommodation || ''} onChange={handleChange} />
        <FormField label="Curral" type="text" name="pens" value={countryHouse.pens || ''} onChange={handleChange} />
        <FormField label="Energia" type="text" name="energy" value={countryHouse.energy ?? 0} onChange={handleChange} />

        <h2>Pastagens e Conservação</h2>
        <FormField label="Capacidade de Rebanho" type="text" name="herdSupport" value={countryHouse.herdSupport ?? 0} onChange={handleChange} />
        <FormField label="Quantidade de pastos" type="text" name="pastures" value={countryHouse.pastures ?? 0} onChange={handleChange} />
        <FormField label="Tipo de Solo" type="text" name="typeOfSoil" value={countryHouse.typeOfSoil ?? 0} onChange={handleChange} />
        <FormField label="Pastagem Predominante" type="text" name="predominantPasture" value={countryHouse.predominantPasture ?? 0} onChange={handleChange} />
        <FormField label="Outras Pastagens" type="text" name="otherPastures" value={countryHouse.otherPastures ?? 0} onChange={handleChange} />
        <FormField label="Conservação das Pastagens" type="text" name="pastureConservation" value={countryHouse.pastureConservation ?? 0} onChange={handleChange} />
        <SelectField label="Cercas de Arame Liso" name="hasSmoothWireFence" value={countryHouse.hasSmoothWireFence ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />

        <h2>Distâncias</h2>
        <FormField label="Distância até Goiânia" type="text" name="distanceOfGyn" value={countryHouse.distanceOfGyn ?? 0} onChange={handleChange} />
        <FormField label="Distância até cidade" type="text" name="distanceOfCity" value={countryHouse.distanceOfCity ?? 0} onChange={handleChange} />
        <FormField label="Distância de Estrada de Terra" type="text" name="distanceDirtRoad" value={countryHouse.distanceDirtRoad ?? 0} onChange={handleChange} />

        <h2>Recursos Naturais</h2>
        <FormField label="Topografia" type="text" name="topography" value={countryHouse.topography ?? 0} onChange={handleChange} />
        <SelectField label="Pedras" name="hasRocks" value={countryHouse.hasRocks ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />
        <FormField label="Rios" type="text" name="rivers" value={countryHouse.rivers ?? 0} onChange={handleChange} />
        <FormField label="Represas" type="text" name="dams" value={countryHouse.dams ?? 0} onChange={handleChange} />
        <SelectField label="Pomar" name="hasOrchard" value={countryHouse.hasOrchard ? 'true' : 'false'} options={[{ id: 'true', name: 'Sim' }, { id: 'false', name: 'Não' }]} onChange={handleChange} />

        <h2>Descrição</h2>
        <FormField label="Descrição" type="textarea" name="description" value={countryHouse.description || ''} onChange={handleChange} />

        <h2>Outras Informações</h2>
        <FormField label="Local das chaves" type="text" name="placeOfKeys" value={countryHouse.placeOfKeys} onChange={handleChange} />
        <FormField label="Hora de Visita" type="text" name="visitingTime" value={countryHouse.visitingTime} onChange={handleChange} />
        <SelectField
         label="Status da Propriedade"
         name="status"
         value={countryHouse.status}
         options={Object.entries(PropertyStatus).map(([key, value]) => ({
           id: value,
           name: PropertyStatusDescription[value] || key
         }))}
         onChange={handleChange}
        />
        <SelectField
          label="Agente"
          name="agentId"
          value={countryHouse.agentId?.toString() || ''}
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
          value={countryHouse.sellerId?.toString() || ''}
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
          {loading ? 'Carregando...' : isEditMode ? 'Salvar Alterações' : 'Adicionar chácara'}
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

export default CountryHouseForm;