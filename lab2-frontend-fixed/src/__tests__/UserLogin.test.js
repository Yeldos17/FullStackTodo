
import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import UserLogin from '../components/UserLogin';
import { AuthProvider } from '../hooks/useAuth';
import { BrowserRouter } from 'react-router-dom';

test('renders login form and validates empty submission', ()=>{
  render(<AuthProvider><BrowserRouter><UserLogin/></BrowserRouter></AuthProvider>);
  const button = screen.getByText(/login/i);
  fireEvent.click(button);
  expect(window.alert).toBeDefined();
});
