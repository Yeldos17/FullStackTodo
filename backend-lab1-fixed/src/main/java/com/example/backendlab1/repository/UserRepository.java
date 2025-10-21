package com.example.backendlab1.repository;

import com.example.backendlab1.model.AvailabilityStatus;
import com.example.backendlab1.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    boolean existsByEmail(String email);

    @Query("select u from UserEntity u where u.availabilityStatus = :status")
    List<UserEntity> findByAvailabilityStatus(AvailabilityStatus status);
}
