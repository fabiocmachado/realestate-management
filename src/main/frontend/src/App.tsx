import React from "react";
import { BrowserRouter as Router, Route, Routes, Navigate } from "react-router-dom";
import { AuthProvider } from "./contexts/AuthContext";
import { useAuth } from "./contexts/AuthContext";
import Login from "./components/Login";
import Dashboard from "./components/Dashboard";
import AgentsList from "./components/AgentList";
import AddAgent from "./components/AddAgent";
import AgentDetails from "./components/AgentDetails";
import EditAgent from "./components/EditAgent";
import DeleteAgent from "./components/DeleteAgent";
import SellersList from "./components/SellerList";
import AddSeller from "./components/AddSeller";
import EditSeller from "./components/EditSeller";
import SellerDetails from "./components/SellerDetails"
import DeleteSeller from "./components/DeleteSeller";
import PropertyList from "./components/PropertyList";
import PropertyDetails from "./components/PropertyDetails";
import AddProperty from "./components/AddProperty";
import EditProperty from "./components/EditProperty";
import Layout from "./components/Layout";
import PrintBlankForm from './components/PrintBlankForm'

const ProtectedRoute: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const { user } = useAuth();
  return user ? <>{children}</> : <Navigate to="/login" replace />;
};

const AppRoutes: React.FC = () => {
  return (
    <Routes>
      <Route path="/login" element={<Login />} />
      <Route path="/" element={<Navigate to="/login" replace />} />

      <Route path="/" element={
        <ProtectedRoute>
          <Layout />
        </ProtectedRoute>
      }>
        <Route path="dashboard" element={<Dashboard />} />
        <Route path="agents" element={<AgentsList />} />
        <Route path="agents/:id" element={<AgentDetails />} />
        <Route path="add-agent" element={<AddAgent />} />
        <Route path="edit-agent/:id" element={<EditAgent />} />
        <Route path="delete-agent/:id" element={<DeleteAgent />} />
        <Route path="sellers" element={<SellersList />} />
        <Route path="sellers/:id" element={<SellerDetails />} />
        <Route path="add-seller" element={<AddSeller />} />
        <Route path="edit-seller/:id" element={<EditSeller />} />
        <Route path="delete-seller/:id" element={<DeleteSeller />} />
        <Route path="properties" element={<PropertyList />} />
        <Route path="properties/:propertyCategory/:propertyCode" element={<PropertyDetails />} />
        <Route path="edit-property/:propertyCategory/:propertyCode" element={<EditProperty />} />
        <Route path="add-property" element={<AddProperty />} />
        <Route path="print/:propertyCategory" element={<PrintBlankForm />} />
      </Route>
    </Routes>
  );
};

const App: React.FC = () => {
  return (
    <AuthProvider>
      <Router>
        <AppRoutes />
      </Router>
    </AuthProvider>
  );
};

export default App;
