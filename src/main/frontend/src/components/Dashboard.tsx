import React, { useEffect, useState } from "react";
import { validateToken } from "../services/authService";
import { useNavigate } from "react-router-dom";
import "../styles/dashboard.css";
import { Outlet } from "react-router-dom";

const Dashboard: React.FC = () => {
  const [isValid, setIsValid] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    const checkToken = async () => {
      const token = localStorage.getItem("authToken");
      if (token) {
        const valid = await validateToken(token);
        setIsValid(valid);
        if (!valid) {
          navigate("/");
        }
      } else {
        navigate("/");
      }
    };
    checkToken();
  }, [navigate]);

  return (
    <div className="dashboard-page">
      <div className="dashboard-container">
        {isValid ? (
          <>
            <p>Lembretes para hoje</p>
            <Outlet />
          </>
        ) : (
          <h1>Token inválido. Por favor, faça login novamente.</h1>
        )}
      </div>
    </div>
  );
};

export default Dashboard;
