import React from "react";
import { useAuth } from "../contexts/AuthContext";

const getGreeting = (): string => {
  const hour = new Date().getHours();
  if (hour < 12) return "Bom dia";
  if (hour < 18) return "Boa tarde";
  return "Boa noite";
};

const getCurrentDate = (): string => {
  const today = new Date();
  const daysOfWeek = [
    "Domingo", "Segunda-feira", "Terça-feira", "Quarta-feira",
    "Quinta-feira", "Sexta-feira", "Sábado",
  ];
  const dayOfMonth = today.getDate();
  const month = today.toLocaleString("pt-BR", { month: "long" });
  const year = today.getFullYear();

  return `${daysOfWeek[today.getDay()]}, ${dayOfMonth} de ${month} de ${year}.`;
};

const Header: React.FC = () => {
  const { user } = useAuth();

  return (
    <header className="bg-white border-b border-gray-300 shadow-md py-5 px-8 flex items-center justify-between max-w-6xl mx-auto rounded-b-md">
      <div className="text-blue-900 font-extrabold text-2xl select-none">
        Visão Imóveis
      </div>

      <div className="text-gray-700 text-center">
        <p className="text-base">
          {getGreeting()},{" "}
          <span className="font-semibold text-blue-800">{user?.name || "Usuário"}</span>.
        </p>
        <p className="text-sm text-gray-500 mt-0.5">{getCurrentDate()}</p>
      </div>
    </header>
  );
};

export default Header;
