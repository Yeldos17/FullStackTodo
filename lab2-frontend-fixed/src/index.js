import React from 'react';
import { createRoot } from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';
import App from './App';
import { ThemeProvider as LocalThemeProvider, useTheme } from './hooks/useTheme';
import { ThemeProvider as SCThemeProvider } from 'styled-components';

function Root(){
  return (
    <LocalThemeProvider>
      <ThemeWrapper>
        <BrowserRouter>
          <App/>
        </BrowserRouter>
      </ThemeWrapper>
    </LocalThemeProvider>
  );
}

function ThemeWrapper({children}){
  const { theme } = useTheme();
  return <SCThemeProvider theme={theme}>{children}</SCThemeProvider>
}

createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <Root/>
  </React.StrictMode>
);
