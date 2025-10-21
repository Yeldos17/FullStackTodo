import React, { useEffect, useState } from 'react';
import api from '../api';
import TaskList from './TaskList';
import styled, { ThemeProvider as StyledThemeProvider } from 'styled-components';
import { useAuth } from '../hooks/useAuth';
import ThemeToggle from './ThemeToggle';
import { DragDropContext } from 'react-beautiful-dnd';
import { useTheme } from '../hooks/useTheme';

const Container = styled.div`
  max-width: 980px;
  margin: 24px auto;
  padding: 24px;
  min-height: 80vh;
  background: ${p => p.theme.background};
  color: ${p => p.theme.text};
`;

const HeaderWrapper = styled.div`
  display:flex;
  justify-content:space-between;
  align-items:center;
  margin-bottom:18px;
`;

const Form = styled.form`
  display:flex;
  gap:8px;
  margin-bottom:16px;
  flex-wrap:wrap;
`;

const Input = styled.input`
  padding:10px;
  border-radius:10px;
  border:1px solid rgba(11,21,54,0.06);
  min-width:220px;
`;

const Select = styled.select`
  padding:10px;
  border-radius:10px;
  border:1px solid rgba(11,21,54,0.06);
`;

const Button = styled.button`
  padding:10px 14px;
  border-radius:10px;
  border:none;
  background:#0b84ff;
  color:#fff;
  cursor:pointer;
`;

export default function Dashboard() {
  const [tasks, setTasks] = useState([]);
  const [users, setUsers] = useState([]);
  const [title, setTitle] = useState('');
  const [priority, setPriority] = useState('MEDIUM');
  const [assigneeId, setAssigneeId] = useState('');
  const auth = useAuth();
  const { theme } = useTheme();

  const authConfig = { headers: { Authorization: `Bearer ${auth.token}` } };

  // Получение всех задач и пользователей
  const fetchAll = async () => {
    if (!auth.token) return;
    try {
      const [tres, ures] = await Promise.all([
        api.get('/tasks', authConfig),
        api.get('/users', authConfig),
      ]);
      setTasks(Array.isArray(tres.data) ? tres.data : []);
      setUsers(Array.isArray(ures.data) ? ures.data : []);
    } catch {
      alert('Failed to load tasks or users');
    }
  };

  useEffect(() => { fetchAll(); }, [auth.token]);

  // Создание новой задачи
  const create = async e => {
    e.preventDefault();
    if (!title) return alert('Title required');
    try {
      const res = await api.post('/tasks', {
        title,
        description: '',
        priority: priority,
        assigneeId: assigneeId || null
      }, authConfig);
      setTasks(prev => [res.data, ...prev]);
      setTitle('');
      setAssigneeId('');
    } catch { alert('Create failed'); }
  };

  // Удаление задачи
  const deleteTask = async id => {
    try {
      await api.delete(`/tasks/${id}`, authConfig);
      setTasks(prev => prev.filter(t => t.id !== id));
    } catch { alert('Delete failed'); }
  };

  // Обновление задачи
  const updateTask = async updated => {
    try {
      await api.put(`/tasks/${updated.id}`, updated, authConfig);
      setTasks(prev => prev.map(t => t.id === updated.id ? updated : t));
    } catch { alert('Update failed'); }
  };

  // Drag and Drop обработчик
  const onDragEnd = async result => {
    if (!result.destination) return;
    const { source, destination } = result;
    if (source.index === destination.index) return;
    const reordered = Array.from(tasks);
    const [moved] = reordered.splice(source.index, 1);
    reordered.splice(destination.index, 0, moved);
    setTasks(reordered);

    try {
      await api.post('/tasks/reorder', { orderedIds: reordered.map(t => t.id) }, authConfig);
    } catch { console.warn('Reorder API not supported'); }
  };

  return (
    <StyledThemeProvider theme={theme}>
      <Container>
        <HeaderWrapper>
          <h1 style={{ margin: 0 }}>Dashboard</h1>
          <ThemeToggle />
        </HeaderWrapper>

        <Form onSubmit={create}>
          <Input
            placeholder="Task title"
            value={title}
            onChange={e => setTitle(e.target.value)}
          />
          <Select value={priority} onChange={e => setPriority(e.target.value)}>
            <option value="HIGH">High</option>
            <option value="MEDIUM">Medium</option>
            <option value="LOW">Low</option>
          </Select>
          <Select value={assigneeId} onChange={e => setAssigneeId(e.target.value)}>
            <option value="">Unassigned</option>
            {users.map(u => (
              <option key={u.id} value={u.id}>{u.email}</option>
            ))}
          </Select>
          <Button type="submit">Create</Button>
        </Form>

        <DragDropContext onDragEnd={onDragEnd}>
          {Array.isArray(tasks) && tasks.length > 0 ? (
            <TaskList tasks={tasks} onDelete={deleteTask} onUpdate={updateTask} />
          ) : (
            <p>No tasks yet</p>
          )}
        </DragDropContext>
      </Container>
    </StyledThemeProvider>
  );
}
