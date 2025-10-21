
import React from 'react';
import {useAuth} from '../hooks/useAuth';
import styled from 'styled-components';
import ThemeToggle from './ThemeToggle';

const Box = styled.div`max-width:720px;margin:24px auto;padding:20px;border-radius:12px;background:${p=>p.theme.surface};box-shadow:${p=>p.theme.cardShadow};`;

export default function Profile(){
  const auth = useAuth();
  return (
    <Box>
      <h2>Profile</h2>
      <p><strong>Email:</strong> {auth.userEmail}</p>
      <div style={{display:'flex',gap:12,alignItems:'center'}}>
        <ThemeToggle/>
        <button onClick={auth.logout}>Logout</button>
      </div>
    </Box>
  );
}
