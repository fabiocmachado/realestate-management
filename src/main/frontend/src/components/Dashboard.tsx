import React, { useEffect, useState } from "react";
import { validateToken } from "../services/authService";
import { useNavigate } from "react-router-dom";
import { Outlet } from "react-router-dom";
import { Calendar, momentLocalizer, Event } from "react-big-calendar";
import moment from "moment";
import "react-big-calendar/lib/css/react-big-calendar.css";
import "../styles/dashboard.css";

const localizer = momentLocalizer(moment);

interface CalendarEvent {
  title: string;
  start: Date;
  end: Date;
}

const Dashboard: React.FC = () => {
  const [isValid, setIsValid] = useState(false);
  const navigate = useNavigate();

  const [events] = useState<CalendarEvent[]>([
    {
      title: "Reunião com cliente",
      start: new Date(2023, 9, 25, 10, 0),
      end: new Date(2023, 9, 25, 11, 0),
    },
    {
      title: "Entrega de relatório",
      start: new Date(2023, 9, 26, 14, 0),
      end: new Date(2023, 9, 26, 15, 0),
    },
  ]);

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
            <h1>Lembretes para hoje</h1>
            <div style={{ height: "500px", margin: "20px 0" }}>
              <Calendar
                localizer={localizer}
                events={events}
                startAccessor="start"
                endAccessor="end"
                defaultView="month"
                views={["month", "week", "day"]}
                messages={{
                  today: "Hoje",
                  previous: "Anterior",
                  next: "Próximo",
                  month: "Mês",
                  week: "Semana",
                  day: "Dia",
                }}
              />
            </div>
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