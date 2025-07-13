import React from "react";
import { useNavigate } from "react-router-dom";
import EventForm from "./forms/EventForm";

const AddEventPage: React.FC = () => {
  const navigate = useNavigate();

  return (
    <div>
      <h2>Criar Novo Evento</h2>
      <EventForm
        onSuccess={() => navigate("/dashboard")}
        onCancel={() => navigate("/dashboard")}
      />
    </div>
  );
};

export default AddEventPage;
