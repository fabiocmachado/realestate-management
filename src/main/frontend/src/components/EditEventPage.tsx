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
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchEvent();
  }, [id]);

  if (loading) return <p>Carregando evento...</p>;
  if (error) return <p>Erro: {error}</p>;
  if (!event) return <p>Evento não encontrado</p>;

  return (
    <div>
      <h2>Editar Evento</h2>
      <EventForm
        event={event}
        onSuccess={() => navigate("/dashboard")}
        onCancel={() => navigate("/dashboard")}
      />
    </div>
  );
};

export default EditEventPage;
