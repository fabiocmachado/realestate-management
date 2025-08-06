import React from "react";
import { Link, Outlet, useNavigate } from "react-router-dom";
import Header from "./Header";
import Footer from "./Footer";
import { useAuth } from "../contexts/AuthContext";

const Layout = () => {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  return (
    <div className="flex flex-col min-h-screen bg-gray-100">
      <Header />

      <nav className="bg-blue-50 border-b border-blue-200 max-w-6xl mx-auto px-6 py-3">
        <div className="flex items-center justify-between text-blue-700 font-medium">
          <ul className="flex space-x-6">
            <li className="relative">
              <Link to="/dashboard" className="hover:text-blue-900 transition-colors">
                Página Inicial
              </Link>
            </li>
            <li className="relative">
              <Link to="/properties" className="hover:text-blue-900 transition-colors">
                Imóveis
              </Link>
            </li>
            {user?.role === "ADMIN" && (
              <>
                <li className="relative">
                  <Link to="/sellers" className="hover:text-blue-900 transition-colors">
                    Proprietários
                  </Link>
                </li>
                <li className="relative">
                  <Link to="/agents" className="hover:text-blue-900 transition-colors">
                    Corretores
                  </Link>
                </li>
              </>
            )}
          </ul>

          <button
            onClick={handleLogout}
            className="bg-blue-50 border-b border-blue-200 max-w-6xl mx-auto px-6 py-3"
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
