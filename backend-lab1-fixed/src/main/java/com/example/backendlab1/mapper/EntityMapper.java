package com.example.backendlab1.mapper;

import com.example.backendlab1.dto.TaskDTO;
import com.example.backendlab1.dto.UserDTO;
import com.example.backendlab1.model.TaskEntity;
import com.example.backendlab1.model.UserEntity;

public class EntityMapper {
    public static UserDTO toDTO(UserEntity u) {
        if (u == null) return null;
        UserDTO d = new UserDTO();
        d.id = u.getId();
        d.username = u.getUsername();
        d.email = u.getEmail();
        d.availabilityStatus = u.getAvailabilityStatus();
        return d;
    }
    public static TaskDTO toDTO(TaskEntity t) {
        if (t == null) return null;
        TaskDTO d = new TaskDTO();
        d.id = t.getId();
        d.title = t.getTitle();
        d.description = t.getDescription();
        d.priorityLevel = t.getPriorityLevel();
        d.ownerId = t.getOwner() != null ? t.getOwner().getId() : null;
        d.creationTimestamp = t.getCreationTimestamp();
        return d;
    }
}
