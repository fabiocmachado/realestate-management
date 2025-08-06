import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import EventForm from "./forms/EventForm";

interface EventData {
  id: number;
  title: string;
  description: string;
  start: string;
  end: string;
}

const EditEventPage: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [event, setEvent] = useState<EventData | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchEvent = async () => {
      setLoading(true);
      setError("");
      try {
        const token = localStorage.getItem("authToken");
        const response = await fetch(`http://localhost:8080/events/${id}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });

        if (!response.ok) throw new Error("Erro ao buscar evento");

        const data = await response.json();
        setEvent({
          ...data,
          start: data.start.slice(0, 16),
          end: data.end.slice(0, 16),
        });
      } catch (err: any) {
        setError(err.message || "Erro desconhecido");
      } finally {
        setLoading(false);
      }
    };

    fetchEvent();
  }, [id]);

  if (loading) {
    return (
      <div className="max-w-3xl mx-auto p-6 text-center text-primary-dark">
        <p>Carregando evento...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="max-w-3xl mx-auto p-6 text-center text-red-600 font-semibold">
        <p>Erro: {error}</p>
      </div>
    );
  }

  if (!event) {
    return (
      <div className="max-w-3xl mx-auto p-6 text-center text-gray-dark">
        <p>Evento não encontrado.</p>
      </div>
    );
  }

  return (
    <div className="max-w-3xl mx-auto p-6 bg-white rounded shadow">
      <h2 className="text-2xl font-semibold mb-6 text-primary-dark">Editar Evento</h2>
      <EventForm
        event={event}
        onSuccess={() => navigate("/dashboard")}
        onCancel={() => navigate("/dashboard")}
      />
    </div>
  );
};

export default EditEventPage;
