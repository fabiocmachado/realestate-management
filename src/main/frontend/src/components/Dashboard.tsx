import React, { useEffect, useState } from "react";
import { validateToken } from "../services/authService";
import { useNavigate } from "react-router-dom";
import { Outlet } from "react-router-dom";
import { Calendar, momentLocalizer } from "react-big-calendar";
import moment from "moment";
import "react-big-calendar/lib/css/react-big-calendar.css";
import "../styles/dashboard.css";

const localizer = momentLocalizer(moment);

interface CalendarEvent {
  id: number;
  title: string;
  start: Date;
  end: Date;
}

const Dashboard: React.FC = () => {
  const [isValid, setIsValid] = useState(false);
  const [events, setEvents] = useState<CalendarEvent[]>([]);
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

  useEffect(() => {
    const fetchEvents = async () => {
      try {
        const token = localStorage.getItem("authToken");
        const response = await fetch("http://localhost:8080/events", {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });

        if (!response.ok) {
          throw new Error("Erro ao buscar eventos");
        }

        const data = await response.json();

        const parsedEvents = data.map((event: any) => ({
          ...event,
          start: new Date(event.start),
          end: new Date(event.end),
        }));

        setEvents(parsedEvents);
      } catch (error) {
        console.error("Erro ao carregar eventos:", error);
      }
    };

    if (isValid) {
      fetchEvents();
    }
  }, [isValid]);

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
