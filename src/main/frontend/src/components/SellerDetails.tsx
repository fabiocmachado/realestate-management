import React, { useEffect, useState } from "react";
import { Seller, PropertyDTO } from "../types/models";
import { useParams, useNavigate } from "react-router-dom";
import { getSellerById } from "../services/sellerService";
import { getProperties } from "../services/propertyService";
import {
  formatCPF,
  formatPhoneNumber,
  formatSellerAddress,
} from "../components/shared/shared";

const SellerDetails: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const [seller, setSeller] = useState<Seller | null>(null);
  const [properties, setProperties] = useState<PropertyDTO[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchDetails = async () => {
      if (!id) {
        setError("ID não fornecido");
        setLoading(false);
        return;
      }

      const numericId = Number(id);
      if (isNaN(numericId)) {
        setError("ID inválido");
        setLoading(false);
        return;
      }

      try {
        const sellerData = await getSellerById(numericId);
        setSeller(sellerData);

        const allPropertiesResponse = await getProperties({ page: 0, size: 1000 });
        const sellerProperties = allPropertiesResponse.content.filter(
          (property: PropertyDTO) =>
            property.id !== undefined && sellerData.properties.includes(property.id)
        );

        setProperties(sellerProperties);
      } catch (err) {
        console.error("Erro ao carregar detalhes do vendedor ou propriedades:", err);
        setError("Erro ao carregar os dados. Por favor, tente novamente.");
      } finally {
        setLoading(false);
      }
    };

    fetchDetails();
  }, [id]);

  const handleEdit = () => {
    if (seller?.id) {
      navigate(`/edit-seller/${seller.id}`);
    }
  };

  if (loading)
    return (
      <div className="text-primary-light font-medium">
        Carregando detalhes do vendedor...
      </div>
    );
  if (error)
    return (
      <div className="text-red-600 font-semibold">{error}</div>
    );
  if (!seller)
    return (
      <div className="text-gray-600 italic">Vendedor não encontrado.</div>
    );

  return (
    <div className="max-w-3xl mx-auto p-6 bg-gray-light rounded-lg shadow-md">
      <header className="mb-6 border-b border-gray-300 pb-2">
        <h2 className="text-2xl font-bold text-primary-dark">Detalhes do Vendedor</h2>
      </header>

      <section className="mb-6 space-y-2 text-gray-800">
        <p><strong>Nome:</strong> {seller.name}</p>
        <p><strong>Email:</strong> {seller.email}</p>
        <p><strong>Telefone:</strong> {formatPhoneNumber(seller.phone)}</p>
        <p><strong>CPF:</strong> {formatCPF(seller.cpf)}</p>
        <p><strong>RG:</strong> {seller.rg}</p>
        <p><strong>Endereço:</strong> {formatSellerAddress(seller)}</p>
      </section>

      <div className="mb-8">
        <button
          onClick={handleEdit}
          className="bg-primary DEFAULT hover:bg-primary-dark text-white font-semibold py-2 px-5 rounded shadow transition-colors"
        >
          Editar
        </button>
      </div>

      <section>
        <h3 className="text-xl font-semibold mb-3 text-primary-dark">Imóveis do Vendedor</h3>
        {properties.length === 0 ? (
          <p className="text-gray-600 italic">Este vendedor não possui imóveis cadastrados.</p>
        ) : (
          <ul className="list-disc list-inside space-y-1 text-gray-700">
            {properties.map((property) => (
              <li key={property.id}>
                <strong>Código do imóvel:</strong> {property.propertyCode}
              </li>
            ))}
          </ul>
        )}
      </section>
    </div>
  );
};

export default SellerDetails;
