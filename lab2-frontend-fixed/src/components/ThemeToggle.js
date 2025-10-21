import React from 'react';
import styled from 'styled-components';
import { useTheme } from '../hooks/useTheme';

const Toggle = styled.button`
  padding:8px 12px;
  border-radius:999px;
  border:1px solid rgba(0,0,0,0.06);
  background:transparent;
  cursor:pointer;
  font-weight:600;
`;

export default function ThemeToggle() {
  const { mode, setMode } = useTheme();
  return (
    <Toggle onClick={() => setMode(mode === 'light' ? 'dark' : 'light')}>
      {mode === 'light' ? '🌙 Dark' : '☀️ Light'}
    </Toggle>
  );
}
