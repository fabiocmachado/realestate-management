import React, { useEffect, useState } from "react";
import { validateToken } from "../services/authService";
import { useNavigate } from "react-router-dom";
import { Outlet } from "react-router-dom";
import { Calendar, momentLocalizer } from "react-big-calendar";
import moment from "moment";
import "react-big-calendar/lib/css/react-big-calendar.css";
import EventForm from "../components/forms/EventForm";

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
  const [loading, setLoading] = useState(true);
  const [showCreateForm, setShowCreateForm] = useState(false);
  const navigate = useNavigate();

  const fetchEvents = async () => {
    try {
      setLoading(true);
      const token = localStorage.getItem("authToken");
      const response = await fetch("http://localhost:8080/events/mine", {
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
    } finally {
      setLoading(false);
    }
  };

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
    if (isValid) {
      fetchEvents();
    }
  }, [isValid]);

  const handleEventCreated = () => {
    setShowCreateForm(false);
    fetchEvents();
  };

  const handleSelectEvent = (event: CalendarEvent) => {
    navigate(`/events/edit/${event.id}`);
  };

  const handleLogout = () => {
    localStorage.removeItem("authToken");
    navigate("/");
  };

  if (!isValid) {
    return (
      <div className="min-h-screen flex items-center justify-center bg-gray-50 p-4">
        <div className="max-w-md w-full bg-white rounded-lg shadow-lg p-8 border border-gray-200">
          <div className="text-center">
            <div className="mx-auto w-16 h-16 bg-red-100 rounded-full flex items-center justify-center mb-4">
              <span className="text-red-600 text-2xl">⚠️</span>
            </div>
            <h1 className="text-xl font-bold text-red-600 mb-2">Token inválido</h1>
            <p className="text-gray-600 mb-6">Por favor, faça login novamente.</p>
            <button
              onClick={() => navigate("/")}
              className="w-full py-2 px-4 bg-blue-600 text-white rounded-md hover:bg-blue-700 transition duration-200 font-semibold"
            >
              Ir para Login
            </button>
          </div>
        </div>
      </div>
    );
  }

  if (loading) {
    return (
      <div className="min-h-screen flex items-center justify-center bg-gray-50 p-4">
        <div className="max-w-md w-full bg-white rounded-lg shadow-lg p-8 border border-gray-200">
          <div className="text-center">
            <div className="mx-auto w-16 h-16 bg-blue-100 rounded-full flex items-center justify-center mb-4">
              <svg className="animate-spin h-8 w-8 text-blue-600" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4"></circle>
                <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
              </svg>
            </div>
            <h1 className="text-lg font-semibold text-gray-800">Carregando eventos...</h1>
            <p className="text-gray-600 mt-2">Por favor, aguarde um momento.</p>
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gray-50 py-6 px-4">
      {/* Header */}
      <div className="max-w-6xl mx-auto mb-6">
        <div className="bg-white rounded-lg shadow-lg p-6 border border-gray-200">
          <div className="flex items-center justify-between">
            <div>
              <h1 className="text-2xl font-bold text-blue-800">Dashboard</h1>
              <p className="text-gray-600 mt-1">Gerencie seus lembretes e eventos</p>
            </div>
           </div>
        </div>
      </div>

      {/* Main Content */}
      <div className="max-w-6xl mx-auto">
        <div className="bg-white rounded-lg shadow-lg border border-gray-200 overflow-hidden">
          {/* Actions Bar */}
          <div className="bg-gray-50 border-b border-gray-200 p-6">
            <div className="flex items-center justify-between">
              <div className="flex items-center space-x-4">
                <h2 className="text-lg font-semibold text-gray-800">Lembretes para hoje</h2>
                <span className="bg-blue-100 text-blue-800 px-3 py-1 rounded-full text-sm font-medium">
                  {events.length} evento{events.length !== 1 ? 's' : ''}
                </span>
              </div>
              <button
                onClick={() => setShowCreateForm(!showCreateForm)}
                className={`px-6 py-2 rounded-md font-semibold transition-all duration-200 ${
                  showCreateForm
                    ? "bg-red-600 text-white hover:bg-red-700 hover:shadow-md"
                    : "bg-blue-600 text-white hover:bg-blue-700 hover:shadow-md"
                }`}
              >
                {showCreateForm ? "✕ Cancelar" : "✚ Novo Evento"}
              </button>
            </div>
          </div>

          {/* Event Form */}
          {showCreateForm && (
            <div className="border-b border-gray-200 p-6 bg-blue-50">
              <div className="bg-white rounded-lg shadow-sm border border-blue-200 p-6">
                <h3 className="text-lg font-semibold text-blue-800 mb-4">Criar Novo Evento</h3>
                <EventForm
                  onSuccess={handleEventCreated}
                  onCancel={() => setShowCreateForm(false)}
                />
              </div>
            </div>
          )}

          {/* Calendar */}
          <div className="p-6">
            <div className="bg-white rounded-lg border border-gray-200 overflow-hidden">
              <div className="h-[600px] p-4">
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
                  onSelectEvent={handleSelectEvent}
                  style={{
                    height: "100%",
                    fontFamily: "inherit",
                  }}
                />
              </div>
            </div>
          </div>
        </div>
      </div>

      {/* Outlet for nested routes */}
      <div className="max-w-6xl mx-auto mt-6">
        <Outlet />
      </div>
    </div>
  );
};

export default Dashboard;