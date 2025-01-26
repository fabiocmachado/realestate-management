export interface AuthResponseDTO {
  token: string;
  name: string;
  email: string;
  role: "ADMIN" | "AGENT";
 }

 export const RoleLabels: Record<AuthResponseDTO['role'], string> = {
     ADMIN: "Administrador",
     AGENT: "Agente"
 }

export type PropertyCategory = 'HOUSE'| 'APARTMENT' | 'PENTHOUSE' | 'TOWNHOUSE'| 'WAREHOUSE' | 'COMMERCIAL_BUILDING' | 'FARM' | 'COUNTRY_HOUSE' | 'URBAN_LAND' | 'COMMERCIAL_AREA' | 'COMMERCIAL_ROOM';

export const PropertyCategoryDescription: Record<PropertyCategory, string> = {
  HOUSE: "Casa",
  TOWNHOUSE: "Sobrado",
  APARTMENT: "Apartamento",
  PENTHOUSE: "Cobertura",
  WAREHOUSE: "Galpão",
  COMMERCIAL_BUILDING: "Prédio comercial",
  FARM: "Fazenda",
  COUNTRY_HOUSE: "Chácara",
  URBAN_LAND: "Terreno",
  COMMERCIAL_AREA: "Área comercial",
  COMMERCIAL_ROOM: "Sala comercial",
};

export enum PropertyStatus {
  AVAILABLE = "AVAILABLE",
  SOLD = "SOLD",
  RESERVED = "RESERVED",
  UNDER_NEGOTIATION = "UNDER_NEGOTIATION",
  INACTIVE = "INACTIVE",
}

export const PropertyStatusDescription: Record<PropertyStatus, string> = {
  [PropertyStatus.AVAILABLE]: "Disponível",
  [PropertyStatus.SOLD]: "Vendido",
  [PropertyStatus.RESERVED]: "Reservado",
  [PropertyStatus.UNDER_NEGOTIATION]: "Em Negociação",
  [PropertyStatus.INACTIVE]: "Inativo",
};

export enum ConservationStatus {
    EXCELLENT = "EXCELLENT",
    GOOD = "GOOD",
    REGULAR = "REGULAR",
    POOR = "POOR"
}

export enum EnergyType {
    NONE = "NONE",
    MONOPHASIC = "MONOPHASIC",
    BIPHASIC = "BIPHASIC",
    TRIPHASIC = "TRIPHASIC"
}

export enum UserRole {
    ADMIN = "ADMIN",
    AGENT = "AGENT"
}

export enum PropertyType {
    RURAL = "RURAL",
    URBAN = "URBAN"
}

export interface LoginDTO {
  email: string;
  password: string;
}

export interface Seller {
  id?: number;
  name: string;
  cpf: string;
  rg: string;
  email: string;
  phone: string;
  street: string;
  block: string;
  lot: string;
  complement: string;
  number: string;
  neighborhood: string,
  city: string;
  state: string;
  properties: number[];
  createdAt?: string;
  updatedAt?: string;
}


export interface Agent {
  id?: number;
  name: string;
  cpf: string;
  rg: string;
  email: string;
  phone: string;
  street: string;
  block: string;
  lot: string;
  complement: string;
  number: string;
  neighborhood: string,
  city: string;
  state: string;
  licenseNumber: string;
  role: UserRole;
  password: string;
  prospectedProperties: number[];
  createdAt?: string;
  updatedAt?: string;
}

export interface PaginatedResponse<T> {
  content: T[];
  totalPages: number;
  totalElements: number;
  pageable: {
    pageNumber: number;
    pageSize: number;
  };
  last: boolean;
  first: boolean;
  number: number;
  size: number;
  sort: {
    sorted: boolean;
    unsorted: boolean;
    empty: boolean;
  };
  numberOfElements: number;
  empty: boolean;
}


  export interface Pageable {
    page: number;
    size: number;
    sort?: string;
  }

  export interface PropertyDTO {
      id?: number;
      propertyCode?: string;
      propertyType?: PropertyType;
      price: number;
      street: string;
      block: string;
      lot: string;
      complement: string;
      number: string;
      neighborhood: string,
      city: string;
      state: string;
      placeOfKeys: string;
      orientation: string;
      description: string;
      status: PropertyStatus;
      visitingTime: string;
      createdAt?: string;
      updatedAt?: string;
      propertyCategory?: string;
      usableArea: number;
      privateArea: number;
      totalArea: number;
      agentId: number;
      sellerId: number;
  }

  export interface ResidentialDTO extends PropertyDTO{
        bedrooms?: number;
        bathrooms?: number;
        garageSpaces?: number;
        hasKitchen?: boolean;
        rentalValue?: number;
      }

  export interface UrbanLandDTO extends ResidentialDTO {
    hasWall: boolean;
    hasAsphalt: boolean;
  }

  export interface ApartmentDTO extends ResidentialDTO {
        apartmentNumber: string;
        numberOfFloors: number;
        livingRoom: number;
        condominiumFee: number;
        offices: number;
        nameOfBuilding: string;
        hasPantry: boolean;
        toilet: number;
        hasBarbecue: boolean;
        hasSwimmingPool: boolean;
        yearsOfConstruction: number;
        hasBalcony: boolean;
        isRented: boolean;
        isInhabited: boolean;
        floorType: string;
        hasAirConditioning: boolean;
        hasSurveillanceCameras: boolean;
        hasCabinets: boolean;
        hasLaundry: boolean;
        hasSauna: boolean;
        suites: number;
        hasBars: boolean;
        hasEmployeeRoom: boolean;
        hasEmployeeBathroom: boolean;
        hasGaragesInRow: boolean;
        numberOfBlocks: number;
        totalOfApartments: number;
        hasPartyHall: boolean;
        hasGameRoom: boolean;
        hasPlayground: boolean;
        hasToyArea: boolean;
        hasSportsCourt: boolean;
        hasElectronicGate: boolean;
        hasElectronicDoorman: boolean;
        hasIntercom: boolean;
        hasGourmetBalcony: boolean;
        elevator: number;
        hasCompartment: boolean;
        hasPrivateSwimmingPool: boolean;
        hasExclusiveSauna: boolean;
        hasMezzanine: boolean;
        hasGym: boolean;
    }

  export interface PenthouseDTO extends ApartmentDTO {}

  export interface HouseDTO extends ResidentialDTO {
    suites: number;
        livingRoom: number;
        offices: number;
        hasLaundry: boolean;
        hasSauna: boolean;
        hasCabinets: boolean;
        hasElectronicGate: boolean;
        hasSurveillanceCameras: boolean;
        hasGarden: boolean;
        hasAirConditioning: boolean;
        hasKennel: boolean;
        floorType: string;
        isInhabited: boolean;
        isRented: boolean;
        hasBalcony: boolean;
        yearsOfConstruction: number;
        hasYard: boolean;
        hasSwimmingPool: boolean;
        hasBarbecueGrill: boolean;
        toilet: number;
        hasSolarEnergy: boolean;
        condominiumFee: number;
        nameOfGatedCommunity: string;
        hasPantry: boolean;
        hasGym: boolean;
        hasEmployeeRoom: boolean;
        hasEmployeeBathroom: boolean;
        hasGaragesInRow: boolean;
        hasIntercom: boolean;
        hasGourmetBalcony: boolean;
        hasElectronicDoorman: boolean;
  }

export interface TownhouseDTO extends HouseDTO {}

export interface CommercialDTO extends PropertyDTO {
  yearsOfConstruction: number;
      isRented: boolean;
      rentalValue: number;
      isInhabited: boolean;
      hasAirConditioning: boolean;
      floorType: string;
      hasSurveillanceCameras: boolean;
      condominiumFee: number;
      nameOfBuilding: string;
      offices: number;
      hasMezzanine: boolean;
      hasKitchen: boolean;
      garageSpaces: number;
      hasGaragesInRow: boolean;
      hasSolarEnergy: boolean;
}

export interface CommercialBuildingDTO extends CommercialDTO {}

export interface CommercialRoomDTO extends CommercialDTO {
    numberOfFloors: number;
    apartmentNumber: string;
}

export interface CommercialAreaDTO extends CommercialDTO {
  hasWall: boolean;
  hasAsphalt: boolean;
}

export interface WarehouseDTO extends CommercialDTO {}

  export interface RuralDTO extends PropertyDTO {
    totalAreaRural: number;
    legalReserveArea: number;
    propertyType: PropertyType;
    rentalValue?: number;
    isRented?: boolean;
  }

  export interface FarmDTO extends RuralDTO {
    name: string;
        formedArea: number;
        typeOfSoil: string;
        predominantPasture: string;
        otherPastures: string;
        pastureConservation: ConservationStatus;
        hasSmoothWireFence: boolean;
        topography: string;
        hasRocks: boolean;
        rivers: string;
        dams: string;
        mainHouse: string;
        staffHouse: string;
        energy: EnergyType;
        hasOrchard: boolean;
        warehouse: string;
        accommodation: string;
        pens: string;
        hasLandingStrip: boolean;
        herdSupport: number;
        distanceOfGyn: number;
        distanceOfCity: number;
        distanceDirtRoad: number;
        pastures: number;
  }

  export interface CountryHouseDTO extends FarmDTO {}
