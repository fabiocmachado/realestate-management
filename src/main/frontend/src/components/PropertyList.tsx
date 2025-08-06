import React, { useEffect, useState, useCallback } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { useAuth } from "../contexts/AuthContext";
import {
  PaginatedResponse,
  PropertyDTO,
  Pageable,
  PropertyStatusDescription,
  PropertyCategoryDescription
} from '../types/models';
import { getProperties, getPropertyByPropertyCode } from '../services/propertyService';
import { formatAddress } from './shared/shared';

const PAGE_SIZE = 20;

const PropertyList: React.FC = () => {
  const [properties, setProperties] = useState<PropertyDTO[]>([]);
  const [totalPages, setTotalPages] = useState(0);
  const [currentPage, setCurrentPage] = useState(1);
  const [loading, setLoading] = useState(false);
  const [statusFilter, setStatusFilter] = useState('AVAILABLE');
  const [categoryFilter, setCategoryFilter] = useState('');
  const [searchCode, setSearchCode] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  const navigate = useNavigate();
  const { user } = useAuth();

  const fetchProperties = useCallback(async (
    page: number,
    status?: string,
    category?: string,
    propertyCode?: string
  ) => {
    setLoading(true);
    setErrorMessage('');

    const pageable: Pageable = { page: page - 1, size: PAGE_SIZE };

    try {
      let response: PaginatedResponse<PropertyDTO>;

      if (propertyCode) {
        response = await getPropertyByPropertyCode(propertyCode);
      } else {
        response = await getProperties(pageable, status, category);
      }

      setProperties(response.content);
      setTotalPages(response.totalPages);

      if (response.content.length === 0) {
        setErrorMessage(
          propertyCode
            ? `Nenhum imóvel encontrado com o código "${propertyCode}".`
            : `Não existem imóveis com o status "${status}" e categoria "${category}".`
        );
      }
    } catch {
      setErrorMessage('Ocorreu um erro ao buscar os imóveis. Tente novamente mais tarde.');
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    fetchProperties(currentPage, statusFilter, categoryFilter, searchCode.trim() || undefined);
  }, [currentPage, statusFilter, categoryFilter, fetchProperties, searchCode]);

  const handleStatusChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setStatusFilter(e.target.value);
    setCurrentPage(1);
  };

  const handleCategoryChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setCategoryFilter(e.target.value);
    setCurrentPage(1);
  };

  const handleSearchCodeChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setSearchCode(e.target.value);
    setCurrentPage(1);
  };

  const handlePageChange = (page: number) => setCurrentPage(page);
  const goToNextPage = () => currentPage < totalPages && setCurrentPage(currentPage + 1);
  const goToPreviousPage = () => currentPage > 1 && setCurrentPage(currentPage - 1);

  const formatCreatedAt = (dateStr: string) =>
    dateStr ? new Date(dateStr).toLocaleDateString('pt-BR') : 'Data não disponível';

  const goToDetails = (category: string, code: string) => {
    navigate(`/properties/${category}/${code}`);
  };

  const renderFilters = () => (
    <div className="flex flex-wrap gap-4 mb-6 p-4 bg-gray-light rounded shadow items-center justify-between">
      <div className="flex flex-wrap gap-4 flex-grow">
        <div className="flex flex-col">
          <label htmlFor="searchCode" className="mb-1 font-semibold text-primary-dark">Buscar por Código:</label>
          <input
            type="text"
            id="searchCode"
            value={searchCode}
            onChange={handleSearchCodeChange}
            placeholder="Digite o código do imóvel"
            className="border border-gray-pale rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-primary-light"
          />
        </div>

        <div className="flex flex-col">
          <label htmlFor="statusFilter" className="mb-1 font-semibold text-primary-dark">Filtrar por Status:</label>
          <select
            id="statusFilter"
            value={statusFilter}
            onChange={handleStatusChange}
            className="border border-gray-pale rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-primary-light"
          >
            <option value="">Todos</option>
            <option value="AVAILABLE">Disponível</option>
            <option value="SOLD">Vendido</option>
            <option value="RESERVED">Reservado</option>
            <option value="UNDER_NEGOTIATION">Em Negociação</option>
            <option value="INACTIVE">Inativo</option>
          </select>
        </div>

        <div className="flex flex-col min-w-[180px]">
          <label htmlFor="categoryFilter" className="mb-1 font-semibold text-primary-dark">Filtrar por Categoria:</label>
          <select
            id="categoryFilter"
            value={categoryFilter}
            onChange={handleCategoryChange}
            className="border border-gray-pale rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-primary-light"
          >
            <option value="">Todas</option>
            <optgroup label="RESIDENCIAIS">
              <option value="APARTMENT">Apartamento</option>
              <option value="PENTHOUSE">Cobertura</option>
              <option value="HOUSE">Casa</option>
              <option value="TOWNHOUSE">Sobrado</option>
              <option value="URBAN_LAND">Terreno</option>
            </optgroup>
            <optgroup label="COMERCIAIS">
              <option value="COMMERCIAL_AREA">Área comercial</option>
              <option value="WAREHOUSE">Galpão</option>
              <option value="COMMERCIAL_BUILDING">Prédio Comercial</option>
              <option value="COMMERCIAL_ROOM">Sala Comercial</option>
            </optgroup>
            <optgroup label="RURAIS">
              <option value="COUNTRY_HOUSE">Chácara</option>
              <option value="FARM">Fazenda</option>
            </optgroup>
          </select>
        </div>
      </div>

      {user?.role === "ADMIN" && (
        <Link
          to="/add-property"
          className="inline-block bg-primary text-white px-4 py-2 rounded shadow hover:bg-primary-dark transition whitespace-nowrap"
        >
          Cadastrar Imóvel
        </Link>
      )}
    </div>
  );

  const renderTable = () => (
    <>
      <table className="min-w-full border-collapse border border-gray-pale shadow-sm">
        <thead className="bg-primary">
          <tr>
            <th className="text-white text-left p-3 border border-primary-dark">Código</th>
            <th className="text-white text-left p-3 border border-primary-dark">Categoria</th>
            <th className="text-white text-left p-3 border border-primary-dark">Preço</th>
            <th className="text-white text-left p-3 border border-primary-dark">Endereço</th>
            <th className="text-white text-left p-3 border border-primary-dark">Status</th>
            <th className="text-white text-left p-3 border border-primary-dark">Cadastro</th>
            <th className="text-white text-left p-3 border border-primary-dark">Ações</th>
          </tr>
        </thead>
        <tbody>
          {properties.map(({ propertyCode, propertyCategory, price, status, createdAt, ...rest }) => (
            <tr
              key={propertyCode}
              className="hover:bg-primary-light cursor-pointer"
              onClick={() => goToDetails(propertyCategory!, propertyCode!)}
            >
              <td className="border border-gray-pale p-2">{propertyCode}</td>
              <td className="border border-gray-pale p-2">
                {PropertyCategoryDescription[propertyCategory as keyof typeof PropertyCategoryDescription] || 'Desconhecida'}
              </td>
              <td className="border border-gray-pale p-2">
                {price?.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' }) || 'Preço não disponível'}
              </td>
              <td className="border border-gray-pale p-2">
                {formatAddress({ propertyCategory, ...rest })}
              </td>
              <td className="border border-gray-pale p-2">{PropertyStatusDescription[status] || 'Status desconhecido'}</td>
              <td className="border border-gray-pale p-2">{formatCreatedAt(createdAt ?? '')}</td>
              <td className="border border-gray-pale p-2">
                <button
                  onClick={e => {
                    e.stopPropagation();
                    goToDetails(propertyCategory!, propertyCode!);
                  }}
                  className="bg-primary text-white rounded px-3 py-1 hover:bg-primary-dark transition"
                >
                  Detalhes
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      <div className="flex justify-center items-center gap-2 mt-4">
        <button
          onClick={goToPreviousPage}
          disabled={currentPage === 1}
          className="px-3 py-1 rounded border border-primary text-primary disabled:opacity-50 disabled:cursor-not-allowed hover:bg-primary hover:text-white transition"
        >
          Anterior
        </button>
        {Array.from({ length: totalPages }, (_, i) => (
          <button
            key={i}
            className={`px-3 py-1 rounded border border-primary ${
              currentPage === i + 1
                ? 'bg-primary text-white'
                : 'text-primary hover:bg-primary hover:text-white'
            } transition`}
            onClick={() => handlePageChange(i + 1)}
          >
            {i + 1}
          </button>
        ))}
        <button
          onClick={goToNextPage}
          disabled={currentPage === totalPages}
          className="px-3 py-1 rounded border border-primary text-primary disabled:opacity-50 disabled:cursor-not-allowed hover:bg-primary hover:text-white transition"
        >
          Próxima
        </button>
      </div>
    </>
  );

  return (
    <div className="max-w-7xl mx-auto p-6">
      {renderFilters()}

      {loading && <p className="text-center text-primary-dark">Carregando...</p>}

      {!loading && errorMessage && (
        <p className="text-center text-red-600 font-semibold">{errorMessage}</p>
      )}

      {!loading && !errorMessage && properties.length > 0 && renderTable()}

      {!loading && !errorMessage && properties.length === 0 && (
        <p className="text-center text-gray-dark">Nenhum imóvel encontrado.</p>
      )}
    </div>
  );
};

export default PropertyList;
