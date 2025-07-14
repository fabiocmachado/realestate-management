import React from "react";
import { Link, Outlet, useNavigate } from "react-router-dom";
import Header from "./Header";
import Footer from "./Footer";
import { useAuth } from "../contexts/AuthContext";

const Layout: React.FC = () => {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  return (
    <div className="flex flex-col min-h-screen">
      <Header />

      <nav className="bg-blue-50 border-b border-blue-200">
        <div className="max-w-6xl mx-auto px-6 py-3 flex items-center justify-between text-blue-700 font-medium">
          <ul className="flex space-x-6">
            <li>
              <Link to="/dashboard" className="hover:text-blue-900 transition-colors">
                Página Inicial
              </Link>
            </li>
            <li>
              <Link to="/properties" className="hover:text-blue-900 transition-colors">
                Imóveis
              </Link>
            </li>
            {user?.role === "ADMIN" && (
              <>
                <li>
                  <Link to="/sellers" className="hover:text-blue-900 transition-colors">
                    Proprietários
                  </Link>
                </li>
                <li>
                  <Link to="/agents" className="hover:text-blue-900 transition-colors">
                    Corretores
                  </Link>
                </li>
              </>
            )}
          </ul>

          {/* Botão Sair alinhado à direita */}
          <button
            onClick={handleLogout}
            className="text-red-600 hover:text-red-700 hover:bg-red-100 border border-red-300 rounded-lg px-4 py-1.5 font-semibold transition duration-200 shadow-sm focus:outline-none focus:ring-2 focus:ring-red-400"
            aria-label="Sair do sistema"
          >
            Sair
          </button>
        </div>
      </nav>

      <main className="flex-grow max-w-6xl mx-auto px-6 py-8">
        <Outlet />
      </main>

      <Footer />
    </div>
  );
};

export default Layout;
