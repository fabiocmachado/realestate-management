import { PropertyDTO,Seller,Agent }from '../../types/models'

export const formatCPF = (cpf: string): string => {
  const cleaned = cpf.replace(/\D/g, "");
  if (cleaned.length === 11) {
    return `${cleaned.slice(0, 3)}.${cleaned.slice(3, 6)}.${cleaned.slice(6, 9)}-${cleaned.slice(9)}`;
  }
  return cpf;
};

export const formatPhoneNumber = (phone?: string | null): string => {
  if (!phone) return '';

  const cleaned = phone.replace(/\D/g, "");
  if (cleaned.length === 11) {
    return `(${cleaned.slice(0, 2)})${cleaned.slice(2, 7)}-${cleaned.slice(7)}`;
  } else if (cleaned.length === 10) {
    return `(${cleaned.slice(0, 2)})${cleaned.slice(2, 6)}-${cleaned.slice(6)}`;
  }
  return phone;
};

export const formatCurrency = (
  value: string | number,
  currency: string = 'BRL',
  locale: string = 'pt-BR'
): string => {
  const numericValue =
    typeof value === 'string'
      ? parseFloat(value.replace(/\D/g, '')) / 100
      : value;

  return new Intl.NumberFormat(locale, {
    style: 'currency',
    currency: currency,
  }).format(numericValue || 0);
};

export const formatAddress = (property: PropertyDTO) => {
  const addressParts = [
    property.street ? `Rua ${property.street}` : '',
    property.block ? `Quadra ${property.block}` : '',
    property.lot ? `Lote ${property.lot}` : '',
    property.number ? `Nº ${property.number}` : '',
    property.complement ? `${property.complement}` : '',
    property.neighborhood ? `Bairro ${property.neighborhood}` : '',
    property.city ? `${property.city}` : '',
    property.state ? `${property.state}` : '',
  ];

  return addressParts.filter(Boolean).join(', ') || 'Endereço não disponível';
};

export const formatSellerAddress = (seller: Seller): string => {
  const addressParts = [
      seller.street ? `Rua ${seller.street}` : '',
      seller.block ? `Quadra ${seller.block}` : '',
      seller.lot ? `Lote ${seller.lot}` : '',
      seller.number ? `Nº ${seller.number}` : '',
      seller.complement ? `${seller.complement}` : '',
      seller.neighborhood ? `Bairro ${seller.neighborhood}` : '',
      seller.city ? `${seller.city}` : '',
      seller.state ? `${seller.state}` : '',
    ];

    return addressParts.filter(Boolean).join(', ') || 'Endereço não disponível';
  };

export const formatAgentAddress = (agent: Agent): string => {
  const addressParts = [
      agent.street ? `Rua ${agent.street}` : '',
      agent.block ? `Quadra ${agent.block}` : '',
      agent.lot ? `Lote ${agent.lot}` : '',
      agent.number ? `Nº ${agent.number}` : '',
      agent.complement ? `${agent.complement}` : '',
      agent.neighborhood ? `Bairro ${agent.neighborhood}` : '',
      agent.city ? `${agent.city}` : '',
      agent.state ? `${agent.state}` : '',
    ];

    return addressParts.filter(Boolean).join(', ') || 'Endereço não disponível';
  };

export const formatArea = (area: number | null | undefined): string => {
  if (area == null || isNaN(area)) return 'Área não disponível';

  return `${area.toLocaleString('pt-BR', {
    minimumFractionDigits: 2,
    maximumFractionDigits: 2
  })}`;
};

export const formatPrice = (value: string): string => {
    const numericValue = value.replace(/\D/g, "");
  const formattedValue = new Intl.NumberFormat("pt-BR", {
    style: "currency",
    currency: "BRL",
  }).format(Number(numericValue) / 100);

  return formattedValue;
};