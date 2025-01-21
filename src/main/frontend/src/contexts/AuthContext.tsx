import React, { createContext, useContext, useState, useEffect } from 'react';
import { AuthResponseDTO } from '../types/models';
import { validateToken } from '../services/authService';

interface AuthContextType {
  user: AuthResponseDTO | null;
  setUser: (user: AuthResponseDTO | null) => void;
  logout: () => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [user, setUser] = useState<AuthResponseDTO | null>(() => {
    const storedUser = localStorage.getItem('user');
    return storedUser ? JSON.parse(storedUser) : null;
  });

  useEffect(() => {
    const validateSession = async () => {
      const token = localStorage.getItem('authToken');
      if (token && user) {
        const isValid = await validateToken(token);
        if (!isValid) {
          logout();
        }
      }
    };

    validateSession();
  }, [user]);

  const logout = () => {
    localStorage.removeItem('authToken');
    localStorage.removeItem('user');
    setUser(null);
  };

  useEffect(() => {
    if (user) {
      localStorage.setItem('user', JSON.stringify(user));
    }
  }, [user]);

  return (
    <AuthContext.Provider value={{ user, setUser, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};
