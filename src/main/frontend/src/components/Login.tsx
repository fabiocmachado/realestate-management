import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../contexts/AuthContext";
import { login } from "../services/authService";

const Login: React.FC = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState<string | null>(null);
  const [showPassword, setShowPassword] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const navigate = useNavigate();
  const { setUser } = useAuth();

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!email || !password) {
      setError("Por favor, preencha todos os campos.");
      return;
    }
    setIsLoading(true);
    setError(null);

    try {
      const response = await login({ email, password });
      setUser(response);
      localStorage.setItem("authToken", response.token);
      navigate("/dashboard");
    } catch (error: any) {
      const errorMessage =
        error.response?.data?.message ||
        "Erro ao realizar login. Verifique suas credenciais.";
      setError(errorMessage);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-50 p-4">
         <div className="max-w-md w-full bg-white rounded-lg shadow-lg p-8 border border-gray-200">
        <h2 className="text-2xl font-bold text-blue-800 mb-6 text-center">Login</h2>

        <form onSubmit={handleLogin} className="space-y-6">
          <div>
            <label htmlFor="email" className="block mb-2 font-semibold text-gray-700">
              E-mail
            </label>
            <input
              type="email"
              id="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              disabled={isLoading}
              required
              className="w-full px-4 py-2 border border-gray-300 rounded-md text-gray-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition duration-200 disabled:bg-gray-100 disabled:cursor-not-allowed"
              placeholder="Digite seu e-mail"
            />
          </div>

          <div>
            <label htmlFor="password" className="block mb-2 font-semibold text-gray-700">
              Senha
            </label>
            <div className="relative">
              <input
                type={showPassword ? "text" : "password"}
                id="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                disabled={isLoading}
                required
                className="w-full px-4 py-2 border border-gray-300 rounded-md text-gray-700 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500 transition duration-200 pr-16 disabled:bg-gray-100 disabled:cursor-not-allowed"
                placeholder="Digite sua senha"
              />
              <button
                type="button"
                onClick={() => setShowPassword(!showPassword)}
                disabled={isLoading}
                aria-label={showPassword ? "Esconder senha" : "Mostrar senha"}
                className="absolute right-3 top-1/2 transform -translate-y-1/2 text-blue-600 hover:text-blue-800 focus:outline-none transition duration-200 disabled:text-gray-400"
              >
                {showPassword ? "👁️" : "🔒"}
              </button>
            </div>
          </div>

          {error && (
            <div className="bg-red-50 border border-red-200 rounded-md p-3">
              <p className="text-red-700 text-sm font-semibold">{error}</p>
            </div>
          )}

          <button
            type="submit"
            disabled={isLoading}
            className={`w-full py-3 rounded-md text-white font-semibold transition-all duration-200 ${
              isLoading
                ? "bg-blue-400 cursor-not-allowed"
                : "bg-blue-600 hover:bg-blue-700 hover:shadow-md active:bg-blue-800"
            }`}
          >
            {isLoading ? (
              <span className="flex items-center justify-center">
                <svg className="animate-spin -ml-1 mr-3 h-5 w-5 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                  <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4"></circle>
                  <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                </svg>
                Entrando...
              </span>
            ) : (
              "Entrar"
            )}
          </button>
        </form>

        <div className="mt-6 text-center">
          <p className="text-gray-600 text-sm">
            Esqueceu sua senha?
            <button className="text-blue-600 hover:text-blue-800 font-semibold ml-1 focus:outline-none focus:underline">
              Clique aqui
            </button>
          </p>
        </div>
      </div>
    </div>
  );
};

export default Login;