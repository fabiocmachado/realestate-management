import axios from "axios";

const api = axios.create({
  baseURL: process.env.REACT_APP_API_BASE_URL || "http://localhost:8080",
  headers: {
    "Content-Type": "application/json",
  },
  withCredentials: true,
});

api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("authToken");

    if (token) {
      config.headers["Authorization"] = `Bearer ${token}`;
    }

    return config;
  },
  (error) => Promise.reject(error)
);

api.interceptors.response.use(
  (response) => response,
  (error) => {
    console.log('Error details:', {
      status: error.response?.status,
      data: error.response?.data,
      config: error.config,
      headers: error.response?.headers,
    });

    if (error.response?.status === 401 || error.response?.status === 403) {
      const authError = error.response?.data?.error === 'Unauthorized' ||
                       error.response?.data?.error === 'Forbidden';

      if (authError) {
        localStorage.removeItem("authToken");
        window.location.href = "/login";
      }
    }

    if (error.response?.status === 500) {
      console.error("Erro interno do servidor. Tente novamente mais tarde.");
    }

    return Promise.reject(error);
  }
);

export default api;