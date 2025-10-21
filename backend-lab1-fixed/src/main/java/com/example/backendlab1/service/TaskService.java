package com.example.backendlab1.service;

import com.example.backendlab1.model.TaskEntity;
import com.example.backendlab1.model.UserEntity;
import com.example.backendlab1.model.PriorityLevel;
import com.example.backendlab1.repository.TaskRepository;
import com.example.backendlab1.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public TaskEntity create(TaskEntity t) {
        if (t.getOwner() != null && t.getOwner().getId() != null) {
            UserEntity u = userRepository.findById(t.getOwner().getId()).orElseThrow(() -> new IllegalArgumentException("Owner not found"));
            t.setOwner(u);
        }
        return taskRepository.save(t);
    }

    public Optional<TaskEntity> findById(Long id) { return taskRepository.findById(id); }
    public List<TaskEntity> findAll() { return taskRepository.findAll(); }
    public List<TaskEntity> findByPriority(PriorityLevel p) { return taskRepository.findByPriorityLevel(p); }
    public List<TaskEntity> findByOwnerId(Long ownerId) { return taskRepository.findByOwnerId(ownerId); }
    public void delete(Long id) { taskRepository.deleteById(id); }

    public TaskEntity assignToAvailableUser(Long taskId) {
        TaskEntity task = taskRepository.findById(taskId).orElseThrow(() -> new IllegalArgumentException("Task not found"));
        List<UserEntity> available = userRepository.findByAvailabilityStatus(com.example.backendlab1.model.AvailabilityStatus.AVAILABLE);
        if (available.isEmpty()) throw new IllegalStateException("No available users");
        UserEntity selected = available.get(0);
        task.setOwner(selected);
        return taskRepository.save(task);
    }

    public void reorderTasks(java.util.List<Long> orderedIds) {
        // Simple approach: top third -> HIGH, middle -> MEDIUM, rest -> LOW
        int n = orderedIds.size();
        for (int i = 0; i < n; i++) {
            Long id = orderedIds.get(i);
            TaskEntity t = taskRepository.findById(id).orElse(null);
            if (t == null) continue;
            int pos = i;
            if (pos < n/3) t.setPriorityLevel(PriorityLevel.HIGH);
            else if (pos < 2*n/3) t.setPriorityLevel(PriorityLevel.MEDIUM);
            else t.setPriorityLevel(PriorityLevel.LOW);
            taskRepository.save(t);
        }
    }

}
