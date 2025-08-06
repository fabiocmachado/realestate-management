import React from 'react';

interface FieldProps {
  label: string;
}

const TextField: React.FC<FieldProps> = ({ label }) => (
  <div className="mb-1">
    <span className="font-semibold">{label}:</span>
    <div className="border-b border-gray-300 w-full h-6 mt-1"></div>
  </div>
);

const ApartmentBlankForm: React.FC = () => {
  return (
    <div className="max-w-[210mm] mx-auto p-4 font-sans text-sm leading-tight text-gray-900 print:p-2 print:text-xs print:leading-snug">
      <h1 className="text-lg font-bold mb-4">Ficha de Apartamento</h1>

      {/* Informações Gerais */}
      <section className="mb-6">
        <h2 className="font-semibold mb-2 border-b border-gray-300 pb-1">Informações Gerais</h2>
        <div className="grid grid-cols-3 gap-x-6">
          <TextField label="Código" />
          <TextField label="Data de cadastro" />
          <TextField label="Preço" />
          <TextField label="Nome do Prédio" />
          <TextField label="Número do Apartamento" />
          <TextField label="Taxa de Condomínio" />
          <TextField label="Endereço" />
          <TextField label="Orientação" />
          <TextField label="Ocupado" />
          <TextField label="Alugado" />
          <TextField label="Valor do Aluguel" />
        </div >
      </section >

      {/* Divisões Internas */}
      <section className="mb-6">
        <h2 className="font-semibold mb-2 border-b border-gray-300 pb-1">Divisões Internas</h2>
        <div className="grid grid-cols-2 gap-x-6">
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
        </div >
      </section >

      {/* Condomínio */}
      <section className="mb-6">
        <h2 className="font-semibold mb-2 border-b border-gray-300 pb-1">Condomínio</h2>
        <div className="grid grid-cols-3 gap-x-6">
          <TextField label="Salão de Festas" />
          <TextField label="Salão de Jogos" />
          <TextField label="Playground" />
          <TextField label="Brinquedoteca" />
          <TextField label="Quadra de Esportes" />
          <TextField label="Piscina" />
          <TextField label="Academia" />
          <TextField label="Sauna" />
          <TextField label="Churrasqueira" />
          <TextField label="Mezanino" />
          <TextField label="Portão Eletrônico" />
          <TextField label="Portaria Eletrônica" />
        </div >
      </section >

      {/* Outras Informações */}
      <section className="mb-6">
        <h2 className="font-semibold mb-2 border-b border-gray-300 pb-1">Outras Informações</h2>
        <div className="grid grid-cols-3 gap-x-6">
          <TextField label="Local das Chaves" />
          <TextField label="Hora de Visita" />
          <TextField label="Captador" />
        </div >
      </section >

      {/* Descrição */}
      <section className="mb-6">
        <h2 className="font-semibold mb-2 border-b border-gray-300 pb-1">Descrição</h2>
        <textarea className="w-full border border-gray-300 rounded resize-none h-24 p-2" />
      </section >

      {/* Proprietário */}
      <section className="mb-6">
        <h2 className="font-semibold mb-2 border-b border-gray-300 pb-1">Proprietário</h2>
        <div className="grid grid-cols-2 gap-x-6">
          <TextField label="Nome" />
          <TextField label="Endereço" />
          <TextField label="RG" />
          <TextField label="CPF" />
          <TextField label="Email" />
          <TextField label="Telefone" />
        </div >
      </section >
    </div >
  );
};

export default ApartmentBlankForm;
