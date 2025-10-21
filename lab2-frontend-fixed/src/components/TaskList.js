import React from 'react';
import { Droppable, Draggable } from 'react-beautiful-dnd';
import styled from 'styled-components';
import TaskCard from './TaskCard';

const ListWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 10px;
  min-height: 100px;
  padding: 8px;
  border-radius: 12px;
  transition: background 0.2s ease;
`;

export default function TaskList({ tasks = [], onDelete, onUpdate }) {
  if (!Array.isArray(tasks)) tasks = []; // защита от ошибок

  return (
    <Droppable droppableId="tasklist">
      {(provided, snapshot) => (
        <ListWrapper
          ref={provided.innerRef}
          {...provided.droppableProps}
          style={{ background: snapshot.isDraggingOver ? '#e0f7ff' : 'transparent' }}
        >
          {tasks.map((task, index) => (
            <Draggable key={String(task.id)} draggableId={String(task.id)} index={index}>
              {(provided, snapshot) => (
                <div
                  ref={provided.innerRef}
                  {...provided.draggableProps}
                  {...provided.dragHandleProps}
                  style={{
                    ...provided.draggableProps.style,
                    userSelect: 'none',
                    opacity: snapshot.isDragging ? 0.7 : 1
                  }}
                >
                  <TaskCard
                    task={task}
                    onDelete={onDelete}
                    onUpdate={onUpdate}
                  />
                </div>
              )}
            </Draggable>
          ))}
          {provided.placeholder}
        </ListWrapper>
      )}
    </Droppable>
  );
}
