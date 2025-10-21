package com.example.backendlab1.controller;

import com.example.backendlab1.dto.CreateTaskRequest;
import com.example.backendlab1.model.TaskEntity;
import com.example.backendlab1.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // ✅ Создание новой задачи
    @PostMapping
    public ResponseEntity<TaskEntity> createTask(@RequestBody CreateTaskRequest request) {
        TaskEntity task = new TaskEntity();
        task.setTitle(request.title);
        task.setDescription(request.description);
        task.setPriorityLevel(request.priorityLevel);

        if (request.ownerId != null) {
            var owner = new com.example.backendlab1.model.UserEntity();
            owner.setId(request.ownerId);
            task.setOwner(owner);
        }

        TaskEntity saved = taskService.create(task);
        return ResponseEntity.status(201).body(saved);
    }

    // ✅ Получить все задачи
    @GetMapping
    public ResponseEntity<List<TaskEntity>> getAll() {
        return ResponseEntity.ok(taskService.findAll());
    }

    // ✅ Автоматическое назначение задачи доступному пользователю
    @PostMapping("/{id}/assign")
    public ResponseEntity<?> assignTask(@PathVariable Long id) {
        try {
            TaskEntity updated = taskService.assignToAvailableUser(id);
            return ResponseEntity.ok(updated);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body(e.getMessage()); // 409 Conflict
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // 400 Bad Request
        }
    }

    // Дополнительный эндпоинт: POST /api/tasks/assign { taskId }
    @PostMapping("/assign")
    public ResponseEntity<?> assignTaskByBody(@RequestBody java.util.Map<String, Long> body) {
        Long taskId = body.get("taskId");
        if (taskId == null) return ResponseEntity.badRequest().body("taskId required");
        try {
            TaskEntity updated = taskService.assignToAvailableUser(taskId);
            return ResponseEntity.ok(updated);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint for reorder: POST /api/tasks/reorder { orderedIds: [1,2,3] }
    @PostMapping("/reorder")
    public ResponseEntity<?> reorderTasks(@RequestBody java.util.Map<String, java.util.List<Long>> body) {
        java.util.List<Long> ordered = body.get("orderedIds");
        if (ordered == null) return ResponseEntity.badRequest().body("orderedIds required");
        taskService.reorderTasks(ordered);
        return ResponseEntity.ok(java.util.Map.of("status","ok"));
    }

}
