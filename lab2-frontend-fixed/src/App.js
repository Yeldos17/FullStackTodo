import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import Dashboard from './components/Dashboard';
import { AuthProvider, useAuth } from './hooks/useAuth';

function PrivateRoute({ children }) {
  const auth = useAuth();
  return auth?.token ? children : <Navigate to="/login" replace />;
}

function Login() {
  const auth = useAuth();
  const [email, setEmail] = React.useState('');
  const [password, setPassword] = React.useState('');

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      await auth.login(email, password);
    } catch {
      alert('Login failed');
    }
  };

  if (auth?.token) return <Navigate to="/dashboard" replace />;

  return (
    <form onSubmit={handleLogin} style={{ padding: 24 }}>
      <h2>Login</h2>
      <input
        placeholder="Email"
        value={email}
        onChange={e => setEmail(e.target.value)}
      />
      <input
        placeholder="Password"
        type="password"
        value={password}
        onChange={e => setPassword(e.target.value)}
      />
      <button type="submit">Login</button>
    </form>
  );
}

export default function App() {
  return (
    <AuthProvider>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/dashboard" element={<PrivateRoute><Dashboard /></PrivateRoute>} />
        <Route path="*" element={<Navigate to="/dashboard" replace />} />
      </Routes>
    </AuthProvider>
  );
}
