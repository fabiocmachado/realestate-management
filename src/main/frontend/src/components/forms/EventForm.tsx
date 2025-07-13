import React, { useState } from "react";

interface EventFormProps {
  event?: {
    id?: number;
    title: string;
    description: string;
    start: string;
    end: string;
  };
  onSuccess: () => void;
  onCancel?: () => void;
}

const EventForm: React.FC<EventFormProps> = ({ event, onSuccess, onCancel }) => {
  const [title, setTitle] = useState(event?.title || "");
  const [description, setDescription] = useState(event?.description || "");
  const [start, setStart] = useState(event?.start || "");
  const [end, setEnd] = useState(event?.end || "");

  const isEditing = !!event?.id;

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    const token = localStorage.getItem("authToken");
    const url = isEditing
      ? `http://localhost:8080/events/${event?.id}`
      : "http://localhost:8080/events";

    const method = isEditing ? "PUT" : "POST";

    const response = await fetch(url, {
      method,
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
      body: JSON.stringify({
        title,
        description,
        start,
        end,
      }),
    });

    if (response.ok) {
      onSuccess();
    } else {
      console.error("Erro ao salvar evento");
    }
  };

  const handleDelete = async () => {
    const confirmed = window.confirm("Tem certeza que deseja excluir este evento?");
    if (!confirmed || !event?.id) return;

    const token = localStorage.getItem("authToken");
    const response = await fetch(`http://localhost:8080/events/${event.id}`, {
      method: "DELETE",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    if (response.ok) {
      onSuccess();
    } else {
      console.error("Erro ao excluir evento");
    }
  };

  return (
    <form onSubmit={handleSubmit} style={{ marginTop: "20px" }}>
      <h3>{isEditing ? "Editar Evento" : "Criar Novo Evento"}</h3>
      <input
        type="text"
        placeholder="Título"
        value={title}
        onChange={(e) => setTitle(e.target.value)}
        required
      />
      <input
        type="text"
        placeholder="Descrição"
        value={description}
        onChange={(e) => setDescription(e.target.value)}
      />
      <input
        type="datetime-local"
        value={start}
        onChange={(e) => setStart(e.target.value)}
        required
      />
      <input
        type="datetime-local"
        value={end}
        onChange={(e) => setEnd(e.target.value)}
        required
      />
      <div style={{ marginTop: "10px" }}>
        <button type="submit">{isEditing ? "Atualizar" : "Salvar"}</button>
        {onCancel && (
          <button type="button" onClick={onCancel} style={{ marginLeft: "10px" }}>
            Cancelar
          </button>
        )}
        {isEditing && (
          <button
            type="button"
            onClick={handleDelete}
            style={{
              marginLeft: "10px",
              backgroundColor: "red",
              color: "white",
              border: "none",
              padding: "6px 12px",
              cursor: "pointer",
            }}
          >
            Excluir Evento
          </button>
        )}
      </div>
    </form>
  );
};

export default EventForm;
