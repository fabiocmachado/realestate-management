import React from 'react';

interface FieldProps {
  label: string;
}

export const WarehouseBlankForm = () => {
  const BooleanField: React.FC<FieldProps> = ({ label }) => (
    <div className="field">
      <span className="label">{label}:</span>
      <div className="checkbox"></div>
    </div>
  );

  const TextField: React.FC<FieldProps> = ({ label }) => (
    <div className="field">
      <span className="label">{label}:</span>
    </div>
  );

  return (
    <div className="print-only">
      <h1 className="heading">Ficha de galpão</h1>
       <div className="two-column">
        <div className="section">
          <h2><TextField label="Código"/></h2>
         </div>
         <div className="section">
          <h2><TextField label="Data de cadastro"/></h2>
         </div>
      </div>
      <div className="section">
        <h2 className="section-heading">Informações Gerais</h2>
        <div className="three-column">
          <div >
            <TextField label="Preço" />
            <TextField label="Endereço" />
          </div>
          <div>
            <BooleanField label="Ocupado" />
            <BooleanField label="Alugado" />
            <TextField label="Valor do Aluguel" />
          </div>
          <div>
            <TextField label="Orientação" />
            <TextField label="Área de terreno" />
            <TextField label="Área construída" />
         </div>
        </div>
      </div>
      <div className="section">
       <div className="two-column">
         <div>
        <h2 className="section-heading">Características</h2>
            <TextField label="Tipo de Piso" />
            <TextField label="Anos de construção" />
            <TextField label="Vagas de garagem" />
            <BooleanField label="Mezanino" />
            <BooleanField label="Cozinha" />
            <TextField label="Vagas de garagem" />
            <BooleanField label="Garagens em Gaveta" />
            <BooleanField label="Energia solar" />
            <BooleanField label="Ar condicionado" />
            <BooleanField label="Câmeras de Vigilância" />
            </div>
         </div>
       </div>
      <div className="section">
        <h2 className="section-heading">Outras Informações</h2>
          <div className="three-column">
          <div>
            <TextField label="Local das Chaves" />
          </div>
          <div className="column">
            <TextField label="Hora de Visita" />
          </div>
          <div className="column">
            <TextField label="Captador" />
          </div>
         </div>
        </div>
      <div className="section">
        <h2 className="section-heading">Descrição</h2>
        <textarea />
      </div>
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

export default WarehouseBlankForm;
