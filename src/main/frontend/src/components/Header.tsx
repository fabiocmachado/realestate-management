import React from "react";
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import '../styles/header.css';

const getGreeting = (): string => {
  const hour = new Date().getHours();
  if (hour < 12) {
    return "Bom dia";
  } else if (hour < 18) {
    return "Boa tarde";
  } else {
    return "Boa noite";
  }
};

const getCurrentDate = (): string => {
  const today = new Date();
  const daysOfWeek = [
    "Domingo", "Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira",
    "Sexta-feira", "Sábado"
  ];
  const dayOfMonth = today.getDate();
  const month = today.toLocaleString('pt-BR', { month: 'long' });
  const year = today.getFullYear();

  return `${daysOfWeek[today.getDay()]}, ${dayOfMonth} de ${month} de ${year}`;
};

const Header: React.FC = () => {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <header className="header">
      <div className="logo">
        <h1>Visão Imóveis</h1>
      </div>
      <div className="greeting">
        <p>{getGreeting()}, {user?.name || "Usuário"}.</p>
        <p>{getCurrentDate()}</p>
      </div>
      <div className="navigation">
        <button onClick={handleLogout}>Sair</button>
      </div>
    </header>
  );
};

export default Header;