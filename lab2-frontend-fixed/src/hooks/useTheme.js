import React, { createContext, useContext, useEffect, useState } from 'react';

const ThemeContext = createContext();

const light = {
  background: '#f7f7fb',
  surface: '#ffffff',
  text: '#0b1536',
  muted: '#6b7280',
  cardShadow: '0 6px 18px rgba(11,21,54,0.07)'
};

const dark = {
  background: '#0b1220',
  surface: '#0f1724',
  text: '#e6eef8',
  muted: '#9aa7bf',
  cardShadow: '0 6px 18px rgba(0,0,0,0.6)'
};

export function ThemeProvider({ children }) {
  const [mode, setMode] = useState(localStorage.getItem('theme') || 'light');
  useEffect(() => { localStorage.setItem('theme', mode); }, [mode]);
  const theme = mode === 'light' ? light : dark;
  return (
    <ThemeContext.Provider value={{ mode, setMode, theme }}>
      {children}
    </ThemeContext.Provider>
  );
}

export function useTheme() {
  return useContext(ThemeContext);
}
