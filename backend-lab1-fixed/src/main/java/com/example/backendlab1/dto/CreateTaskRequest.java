package com.example.backendlab1.dto;

import jakarta.validation.constraints.NotBlank;
import com.example.backendlab1.model.PriorityLevel;

public class CreateTaskRequest {
    @NotBlank public String title;
    public String description;
    public PriorityLevel priorityLevel = PriorityLevel.MEDIUM;
    public Long ownerId;
}
