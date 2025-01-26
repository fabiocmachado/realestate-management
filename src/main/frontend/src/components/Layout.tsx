import React from "react";
import { Link } from "react-router-dom";
import Header from "./Header";
import Footer from "./Footer";
import { Outlet } from "react-router-dom";
import { useAuth } from "../contexts/AuthContext";
import '../styles/layout.css';

const Layout: React.FC = () => {
  const { user } = useAuth();

  return (
    <div className="layout">
      <Header />
      <nav className="nav">
        <ul>
          <li>
            <Link to="/dashboard">Página Inicial</Link>
          </li>
          <li>
            <Link to="/properties">Imóveis</Link>
          </li>
          {user?.role === "ADMIN" && (
            <>
              <li>
                <Link to="/sellers">Proprietários</Link>
              </li>
              <li>
                <Link to="/agents">Corretores</Link>
              </li>
            </>
          )}
        </ul>
      </nav>
      <main className="main-content">
        <Outlet />
      </main>
      <Footer />
    </div>
  );
};

export default Layout;