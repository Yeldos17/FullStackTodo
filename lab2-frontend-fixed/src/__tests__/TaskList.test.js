
import React from 'react';
import { render, screen } from '@testing-library/react';
import TaskList from '../components/TaskList';

const tasks = [
  { id: 1, title: 'A', priority: 'HIGH' },
  { id: 2, title: 'B', priority: 'LOW' }
];

test('renders tasks in the list', ()=>{
  render(<TaskList tasks={tasks} onChange={()=>{}}/>);
  expect(screen.getByText('A')).toBeInTheDocument();
  expect(screen.getByText('B')).toBeInTheDocument();
});
