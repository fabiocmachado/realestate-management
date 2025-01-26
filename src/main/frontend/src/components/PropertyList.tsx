import React, { useEffect, useState, useCallback } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { useAuth } from "../contexts/AuthContext";
import { PaginatedResponse, PropertyDTO, Pageable, PropertyStatusDescription, PropertyCategoryDescription } from '../types/models';
import { getProperties, getPropertyByPropertyCode } from '../services/propertyService';
import '../styles/propertyList.css'

const PropertyList: React.FC = () => {
  const [properties, setProperties] = useState<PropertyDTO[]>([]);
  const [totalPages, setTotalPages] = useState<number>(0);
  const [currentPage, setCurrentPage] = useState<number>(1);
  const [loading, setLoading] = useState<boolean>(false);
  const [statusFilter, setStatusFilter] = useState<string>('AVAILABLE');
  const [searchCode, setSearchCode] = useState<string>('');
  const [categoryFilter, setCategoryFilter] = useState<string>('');
  const [errorMessage, setErrorMessage] = useState<string>('');
  const navigate = useNavigate();
  const { user } = useAuth();

  const fetchProperties = useCallback(async (page: number, status?: string, category?: string, propertyCode?: string) => {
    setLoading(true);
    setErrorMessage('');
    const pageable: Pageable = {
      page: page - 1,
      size: 20,
    };

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
        setErrorMessage(`Não existem imóveis com o status "${status}" e categoria "${category}"`);
      }
    } catch (error) {
      console.error('Erro ao buscar propriedades', error);
      setErrorMessage('Ocorreu um erro ao buscar os imóveis. Tente novamente mais tarde.');
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    if (searchCode) {
      fetchProperties(currentPage, statusFilter, categoryFilter, searchCode);
    } else {
      fetchProperties(currentPage, statusFilter, categoryFilter);
    }
  }, [currentPage, statusFilter, categoryFilter, fetchProperties, searchCode]);

  const handleStatusChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    setStatusFilter(event.target.value);
    setCurrentPage(1);
  };

  const handleCategoryChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    setCategoryFilter(event.target.value);
    setCurrentPage(1);
  };

  const handleSearchCodeChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSearchCode(event.target.value);
    setCurrentPage(1);
  };

  const handlePageChange = (page: number) => {
    setCurrentPage(page);
  };

  const goToNextPage = () => {
    if (currentPage < totalPages) {
      const nextPage = currentPage + 1;
      setCurrentPage(nextPage);
    }
  };

  const goToPreviousPage = () => {
    if (currentPage > 1) {
      const prevPage = currentPage - 1;
      setCurrentPage(prevPage);
    }
  };

  const formatAddress = (property: PropertyDTO) => {
    const addressParts = [
      property.street,
      property.block,
      property.lot,
      property.number,
      property.complement,
      property.city,
      property.state,
    ];

    return addressParts.filter(Boolean).join(', ') || 'Endereço não disponível';
  };

  const formatCreatedAt = (createdAt: string) => {
    if (!createdAt) return 'Data não disponível';
    const date = new Date(createdAt);
    return date.toLocaleDateString('pt-BR');
  };

  const goToDetails = (propertyCategory: string, propertyCode: string) => {
    navigate(`/properties/${propertyCategory}/${propertyCode}`);
  };

  return (
    <div>
     {user?.role === "ADMIN" && (
      <div className="add-property-link">
        <Link to="/add-property">Cadastrar Imóvel</Link>
      </div>
      )}
      <div className="filter-group">
        <div className="filter-item">
          <label htmlFor="searchCode">Buscar por Código:</label>
          <input
            type="text"
            id="searchCode"
            value={searchCode}
            onChange={handleSearchCodeChange}
            placeholder="Digite o código do imóvel"
          />
        </div>

        <div className="filter-item">
          <label htmlFor="statusFilter">Filtrar por Status:</label>
          <select id="statusFilter" value={statusFilter} onChange={handleStatusChange}>
            <option value="">Todos</option>
            <option value="AVAILABLE">Disponível</option>
            <option value="SOLD">Vendido</option>
            <option value="RESERVED">Reservado</option>
            <option value="UNDER_NEGOTIATION">Em Negociação</option>
            <option value="INACTIVE">Inativo</option>
          </select>
        </div>

        <div className="filter-item">
          <label htmlFor="categoryFilter">Filtrar por Categoria:</label>
          <select id="categoryFilter" value={categoryFilter} onChange={handleCategoryChange}>
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

      {loading ? (
        <p>Carregando...</p>
      ) : errorMessage ? (
        <p style={{ color: 'red' }}>{errorMessage}</p>
      ) : properties.length > 0 ? (
        <>
          <table>
            <thead>
              <tr>
                <th>Código</th>
                <th>Categoria</th>
                <th>Preço</th>
                <th>Endereço</th>
                <th>Status</th>
                <th>Cadastro</th>
                <th>Ações</th>
              </tr>
            </thead>
            <tbody>
              {properties.map((property) => (
                <tr key={property.propertyCode}>
                  <td>{property.propertyCode}</td>
                  <td>
                    {PropertyCategoryDescription[property.propertyCategory as keyof typeof PropertyCategoryDescription] || 'Desconhecida'}
                  </td>
                  <td>{property.price?.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' }) || 'Preço não disponível'}</td>
                  <td>{formatAddress(property)}</td>
                  <td>{PropertyStatusDescription[property.status] || 'Status desconhecido'}</td>
                  <td>{formatCreatedAt(property.createdAt ?? '')}</td>
                  <td>
                    <button onClick={() => goToDetails(property.propertyCategory!, property.propertyCode!)}>
                      Detalhes
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>

          <div className="pagination">
            <button onClick={goToPreviousPage} disabled={currentPage === 1}>
              Anterior
            </button>
            {Array.from({ length: totalPages }, (_, index) => (
              <button
                key={index}
                className={currentPage === index + 1 ? 'active' : ''}
                onClick={() => handlePageChange(index + 1)}
              >
                {index + 1}
              </button>
            ))}
            <button onClick={goToNextPage} disabled={currentPage === totalPages}>
              Próxima
            </button>
          </div>
        </>
      ) : (
        <p>Nenhum imóvel encontrado.</p>
      )}
    </div>
  );
};

export default PropertyList;
