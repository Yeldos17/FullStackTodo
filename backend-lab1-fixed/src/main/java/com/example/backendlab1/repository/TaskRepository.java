package com.example.backendlab1.repository;

import com.example.backendlab1.model.PriorityLevel;
import com.example.backendlab1.model.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    @Query("select t from TaskEntity t where t.priorityLevel = :priority")
    List<TaskEntity> findByPriorityLevel(PriorityLevel priority);

    @Query("select t from TaskEntity t where t.owner.id = :ownerId")
    List<TaskEntity> findByOwnerId(Long ownerId);
}
