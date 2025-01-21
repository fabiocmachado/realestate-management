import React from 'react';
import '../../styles/blankForm.css';

interface FieldProps {
  label: string;
}

export const FarmBlankForm = () => {
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
        <h1 className="heading">Ficha de fazenda</h1>
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
              <TextField label="Nome da fazenda" />
              <TextField label="Endereço" />
            </div>
            <div>
              <BooleanField label="Arrendada" />
              <TextField label="Valor do arrendamento" />
            </div>
            <div>
              <TextField label="Área total" />
              <TextField label="Área formada" />
              <TextField label="Área de reserva legal" />
           </div>
          </div>
        </div>

      <div className="two-column">
        <div className="section">
          <h2 className="section-heading">Infraestutura</h2>
              <BooleanField label="Casa principal" />
              <BooleanField label="Casa para funcionários" />
              <BooleanField label="Galpão" />
              <BooleanField label="Alojamento" />
              <BooleanField label="Curral" />
              <BooleanField label="Energia" />
         </div>
      <div className="section">
          <h2 className="section-heading">Pastagens e Conservação</h2>
              <TextField label="Capacidade de Rebanho" />
              <TextField label="Quantidade de pastos" />
              <TextField label="Tipo de Solo" />
              <TextField label="Pastagem Predominante" />
              <TextField label="Outras Pastagenss" />
              <TextField label="Conservação das Pastagens" />
              <BooleanField label="Cercas de Arame Liso" />
      </div>
     </div>
      <div className="section">
          <h2 className="section-heading">Distâncias</h2>
              <TextField label="Distância até Goiânia" />
              <TextField label="Distância até cidade" />
              <TextField label="Distância de Estrada de Terra" />
              </div>
      <div className="section">
          <h2 className="section-heading">Recursos Naturais</h2>
             <TextField label="Topografia" />
             <BooleanField label="Pedras" />
             <TextField label="Rios" />
             <TextField label="Represas" />
             <BooleanField label="Pomar" />
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

export default FarmBlankForm;
