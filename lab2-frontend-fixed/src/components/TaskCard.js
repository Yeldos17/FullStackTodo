import React from 'react';
import styled from 'styled-components';

const Card = styled.div`
  padding:16px;
  border-radius:14px;
  box-shadow:0 1px 3px rgba(0,0,0,0.1);
  background:${p => p.theme.surface};
  display:flex;
  justify-content:space-between;
  align-items:center;
  border:1px solid rgba(11,21,54,0.04);
`;

const Left = styled.div`
  display:flex;
  flex-direction:column;
  gap:6px;
`;

const Title = styled.strong`
  font-size:15px;
  color:${p => p.theme.text};
`;

const Subtitle = styled.small`
  color: #7a7a7a;
`;

const Priority = styled.span`
  padding:6px 10px;
  border-radius:12px;
  font-weight:700;
  color:white;
  background: ${p => p.priority==='HIGH' ? '#d9534f' : p.priority==='MEDIUM' ? '#f0ad4e' : '#5cb85c'};
`;

const Actions = styled.div`
  display:flex;
  gap:8px;
`;

export default function TaskCard({ task, onDelete, onUpdate }) {
  const handleEdit = () => {
    const newTitle = prompt('New title', task.title);
    if (!newTitle) return;
    onUpdate({ ...task, title: newTitle });
  };

  return (
    <Card>
      <Left>
        <Title>{task.title}</Title>
        <Subtitle>{task.description || ''}</Subtitle>
      </Left>
      <div style={{ display: 'flex', gap: 12, alignItems: 'center' }}>
        <Priority priority={task.priority}>{task.priority}</Priority>
        <div>{task.assignee?.email || 'Unassigned'}</div>
        <Actions>
          <button onClick={handleEdit}>Edit</button>
          <button onClick={() => onDelete(task.id)}>Delete</button>
        </Actions>
      </div>
    </Card>
  );
}
