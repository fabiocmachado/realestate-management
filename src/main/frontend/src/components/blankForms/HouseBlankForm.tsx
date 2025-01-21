import React from 'react';

interface FieldProps {
  label: string;
}

export const HouseBlankForm = () => {
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
      <h1 className="heading">Ficha de Casa</h1>
       <div className="two-column">
        <div className="section">
          <h2><TextField label="Código"/></h2>
         </div>
         <div className="section">
          <h2><TextField label="Data de cadastro"/></h2>
         </div>
      </div>
      {/* Informações Gerais */}
      <div className="section">
        <h2 className="section-heading">Informações Gerais</h2>
        <div className="three-column">
          <div >
            <TextField label="Preço" />
            <TextField label="Nome do condomínio" />
            <TextField label="Taxa de Condomínio" />
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
            <BooleanField label="Cozinha" />
            <BooleanField label="Despensa" />
            <BooleanField label="Sacada" />
            <BooleanField label="Varanda Goumert" />
            <BooleanField label="Lavanderia" />
            <BooleanField label="Quarto para Funcionário" />
            <BooleanField label="Banheiro para Funcionário" />
    </div>
  <div>
        <h2 className="section-heading">Comodidades</h2>
            <TextField label="Tipo de Piso" />
            <TextField label="Vagas de garagem" />
            <BooleanField label="Garagens em Gaveta" />
            <BooleanField label="Escaninho" />
            <BooleanField label="Armários" />
            <BooleanField label="Interfone" />
            <BooleanField label="Câmeras de Vigilância" />
            <BooleanField label="Rede de Segurança" />
            <BooleanField label="Ar-condicionado" />
            <BooleanField label="Piscina" />
            <BooleanField label="Churrasqueira" />
            <BooleanField label="Sauna" />
            <BooleanField label="Portão Eletrônico" />
            <BooleanField label="Portaria Eletrônica" />
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
          <div className="column">
            <TextField label="Hora de Visita" />
          </div>
          <div className="column">
            <TextField label="Captador" />
          </div>
         </div>
        </div>

      {/* Descrição */}
      <div className="section">
        <h2 className="section-heading">Descrição</h2>
        <textarea />
      </div>

      {/* Propretário */}
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

export default HouseBlankForm;
