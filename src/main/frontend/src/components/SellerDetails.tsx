import React, { useEffect, useState } from "react";
import { Seller, PropertyDTO } from "../types/models";
import { useParams, useNavigate } from "react-router-dom";
import { getSellerById } from "../services/sellerService";
import { getProperties } from "../services/propertyService";
import { formatCPF, formatPhoneNumber } from "../components/shared/shared";
import '../styles/sellerDetails.css';

interface SellerDetailsProps {}

const formatAddress = (seller: Seller) => {
  const addressParts = [
    seller.street,
    seller.block,
    seller.lot,
    seller.number,
    seller.complement,
    seller.neighborhood,
    seller.city,
    seller.state,
  ];
  return addressParts.filter(Boolean).join(", ");
};

const SellerDetails: React.FC<SellerDetailsProps> = () => {
  const { id } = useParams<{ id: string }>();
  const [seller, setSeller] = useState<Seller | null>(null);
  const [properties, setProperties] = useState<PropertyDTO[]>([]);
  const navigate = useNavigate();
  const [error, setError] = useState<string | null>(null);
  const [loadingProperties, setLoadingProperties] = useState<boolean>(false);

  useEffect(() => {
    if (id) {
      const numericId = parseInt(id, 10);
      if (!isNaN(numericId)) {
        getSellerById(numericId)
          .then(async (data) => {
            setSeller(data);
            setLoadingProperties(true);
            try {
              const response = await getProperties({ page: 0, size: 1000 }, undefined, undefined);
              const fetchedProperties = response.content.filter((prop: PropertyDTO) =>
                prop.id !== undefined && data.properties.includes(prop.id)
              );
              setProperties(fetchedProperties);
            } catch (err) {
              console.error("Erro ao carregar detalhes das propriedades:", err);
              setError("Erro ao carregar detalhes das propriedades. Por favor, tente novamente.");
            } finally {
              setLoadingProperties(false);
            }
          })
          .catch((error) => {
            console.error("Erro ao carregar os detalhes do vendedor", error);
            setError("Erro ao carregar os detalhes do vendedor. Por favor, tente novamente.");
          });
      } else {
        setError("ID inválido");
      }
    } else {
      setError("ID não fornecido");
    }
  }, [id]);

  if (error) {
    return <div className="error-message">{error}</div>;
  }

  if (!seller || loadingProperties) {
    return <div className="loading-message">Carregando...</div>;
  }

  const handleEdit = (id: number) => {
    navigate(`/edit-seller/${id}`);
  };

  return (
    <div className="seller-details-container">
      <div className="seller-details-header">
        <h2>Detalhes do vendedor</h2>
      </div>
      <div className="seller-details-info">
        <p><strong>Nome:</strong> {seller.name}</p>
        <p><strong>Email:</strong> {seller.email}</p>
        <p><strong>Telefone:</strong> {formatPhoneNumber(seller.phone)}</p>
        <p><strong>CPF:</strong> {formatCPF(seller.cpf)}</p>
        <p><strong>RG:</strong> {seller.rg}</p>
        <p><strong>Endereço:</strong> {formatAddress(seller)}</p>
      </div>
      <div><button onClick={() => handleEdit(seller.id!)}>Editar</button></div>
      <div className="seller-details-properties">
        <p><strong>Imóveis:</strong></p>
        <ul>
          {properties.map((property) => (
            <li key={property.id}><strong>Código do imóvel:</strong> {property.propertyCode}</li>
          ))}
        </ul>
      </div>
    </div>
  );
};

export default SellerDetails;