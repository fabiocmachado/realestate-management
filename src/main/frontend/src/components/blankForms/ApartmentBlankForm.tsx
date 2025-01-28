import React from 'react';

interface FieldProps {
  label: string;
}

export const ApartmentBlankForm = () => {
  const TextField: React.FC<FieldProps> = ({ label }) => (
    <div className="field">
      <span className="label">{label}:</span>
    </div>
  );

  return (
    <div className="print-only">
      <h1 className="heading">Ficha de Apartamento</h1>
      <div className="section">
        <div className="two-column">
          <div className="section">
            <h2><TextField label="Código" /></h2>
          </div>
          <div className="section">
            <h2><TextField label="Data de cadastro" /></h2>
          </div>
        </div>
      </div>

      {/* Informações Gerais */}
      <div className="section">
        <h2 className="section-heading">Informações Gerais</h2>
        <div className="three-column">
          <div>
            <TextField label="Preço" />
            <TextField label="Nome do Prédio" />
            <TextField label="Número do Apartamento" />
            <TextField label="Taxa de Condomínio" />
            <TextField label="Endereço" />
          </div>
          <div>
            <TextField label="Orientação" />
            <TextField label="Ocupado" />
            <TextField label="Alugado" />
            <TextField label="Valor do Aluguel" />
          </div>
          <div>
            <TextField label="Área Total" />
            <TextField label="Área Privativa" />
            <TextField label="Área Útil" />
          </div>
        </div>
      </div>

      {/* Divisões Internas */}
      <div className="section">
        <div className="two-column">
          <div>
            <h2 className="section-heading">Divisões Internas</h2>
            <TextField label="Quartos" />
            <TextField label="Suítes" />
            <TextField label="Salas" />
            <TextField label="Banheiro Social" />
            <TextField label="Lavabo" />
            <TextField label="Escritórios" />
            <TextField label="Cozinha" />
            <TextField label="Despensa" />
            <TextField label="Sacada" />
            <TextField label="Varanda Gourmet" />
            <TextField label="Lavanderia" />
            <TextField label="Quarto para Funcionário" />
            <TextField label="Banheiro para Funcionário" />
          </div>
          <div>
            <h2 className="section-heading">Comodidades</h2>
            <TextField label="Tipo de Piso" />
            <TextField label="Vagas de garagem" />
            <TextField label="Garagens em Gaveta" />
            <TextField label="Escaninho" />
            <TextField label="Armários" />
            <TextField label="Interfone" />
            <TextField label="Câmeras de Vigilância" />
            <TextField label="Rede de Segurança" />
            <TextField label="Ar-condicionado" />
            <TextField label="Sauna Exclusiva" />
            <TextField label="Piscina Exclusiva" />
          </div>
        </div>
      </div>

      {/* Condomínio */}
      <div className="section">
        <h2 className="section-heading">Condomínio</h2>
        <div className="three-column">
          <div>
            <TextField label="Salão de Festas" />
            <TextField label="Salão de Jogos" />
            <TextField label="Playground" />
            <TextField label="Brinquedoteca" />
            <TextField label="Quadra de Esportes" />
            <TextField label="Piscina" />
          </div>
          <div>
            <TextField label="Academia" />
            <TextField label="Sauna" />
            <TextField label="Churrasqueira" />
            <TextField label="Mezanino" />
            <TextField label="Portão Eletrônico" />
            <TextField label="Portaria Eletrônica" />
          </div>
          <div>
            <TextField label="Anos de construção" />
            <TextField label="Elevador" />
            <TextField label="Blocos" />
            <TextField label="Total de apartamentos" />
            <TextField label="Total de andares" />
          </div>
        </div>
      </div>

      {/* Outras Informações */}
      <div className="section">
        <h2 className="section-heading">Outras Informações</h2>
        <div className="three-column">
          <div>
            <TextField label="Local das Chaves" />
          </div>
          <div>
            <TextField label="Hora de Visita" />
          </div>
          <div>
            <TextField label="Captador" />
          </div>
        </div>
      </div>

      {/* Descrição */}
      <div className="section">
        <h2 className="section-heading">Descrição</h2>
        <textarea />
      </div>

      {/* Proprietário */}
      <div className="section">
        <h2 className="section-heading">Proprietário</h2>
        <div className="two-column">
          <div>
            <TextField label="Nome" />
            <TextField label="Endereço" />
            <TextField label="RG" />
          </div>
          <div>
            <TextField label="CPF" />
            <TextField label="Email" />
            <TextField label="Telefone" />
          </div>
        </div>
      </div>
    </div>
  );
};

export default ApartmentBlankForm;
