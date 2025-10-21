import axios from "axios";

// Создаём экземпляр axios для запросов к backend
const api = axios.create({
  baseURL: "http://localhost:8080/api", // URL бэкенда
  headers: {
    "Content-Type": "application/json",
  },
  withCredentials: false, // CORS без cookie, только через JWT
});

// 👉 Перехватчик для добавления токена
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

// ⚙️ Перехватчик ответов — если токен недействителен, удаляем его
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response && error.response.status === 403) {
      console.warn("⛔ Доступ запрещён — токен недействителен. Разлогиниваем...");
      localStorage.removeItem("token");
      window.location.href = "/login";
    }
    return Promise.reject(error);
  }
);

export default api;
