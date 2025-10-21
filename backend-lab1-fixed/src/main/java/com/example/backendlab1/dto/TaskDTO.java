package com.example.backendlab1.dto;

import com.example.backendlab1.model.PriorityLevel;
import java.time.Instant;

public class TaskDTO {
    public Long id;
    public String title;
    public String description;
    public PriorityLevel priorityLevel;
    public Long ownerId;
    public Instant creationTimestamp;
}
