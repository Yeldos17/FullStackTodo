package com.example.backendlab1.config;

import com.example.backendlab1.model.PriorityLevel;
import com.example.backendlab1.model.TaskEntity;
import com.example.backendlab1.model.UserEntity;
import com.example.backendlab1.model.AvailabilityStatus;
import com.example.backendlab1.repository.TaskRepository;
import com.example.backendlab1.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {
    private final UserRepository userRepo;
    private final TaskRepository taskRepo;

    public DataInitializer(UserRepository userRepo, TaskRepository taskRepo) { this.userRepo = userRepo; this.taskRepo = taskRepo; }

    @PostConstruct
    public void init() {
        if (userRepo.count() > 0) return;
        // create 3 users
        UserEntity u1 = new UserEntity(); u1.setUsername("alice"); u1.setEmail("alice@example.com"); u1.setHashedPassword(new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode("password1")); u1.setAvailabilityStatus(AvailabilityStatus.AVAILABLE);
        UserEntity u2 = new UserEntity(); u2.setUsername("bob"); u2.setEmail("bob@example.com"); u2.setHashedPassword(new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode("password2")); u2.setAvailabilityStatus(AvailabilityStatus.BUSY);
        UserEntity u3 = new UserEntity(); u3.setUsername("carol"); u3.setEmail("carol@example.com"); u3.setHashedPassword(new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder().encode("password3")); u3.setAvailabilityStatus(AvailabilityStatus.AVAILABLE);

        userRepo.save(u1); userRepo.save(u2); userRepo.save(u3);

        // create 5 tasks
        TaskEntity t1 = new TaskEntity(); t1.setTitle("Setup project"); t1.setDescription("Initialize repo and project"); t1.setPriorityLevel(PriorityLevel.HIGH); t1.setOwner(u1);
        TaskEntity t2 = new TaskEntity(); t2.setTitle("Database schema"); t2.setDescription("Design schema"); t2.setPriorityLevel(PriorityLevel.MEDIUM); t2.setOwner(u1);
        TaskEntity t3 = new TaskEntity(); t3.setTitle("Authentication"); t3.setDescription("Implement JWT auth"); t3.setPriorityLevel(PriorityLevel.CRITICAL); t3.setOwner(null);
        TaskEntity t4 = new TaskEntity(); t4.setTitle("Write tests"); t4.setDescription("Add unit tests"); t4.setPriorityLevel(PriorityLevel.HIGH); t4.setOwner(u3);
        TaskEntity t5 = new TaskEntity(); t5.setTitle("Documentation"); t5.setDescription("Write README"); t5.setPriorityLevel(PriorityLevel.LOW); t5.setOwner(null);

        taskRepo.save(t1); taskRepo.save(t2); taskRepo.save(t3); taskRepo.save(t4); taskRepo.save(t5);
    }
}
