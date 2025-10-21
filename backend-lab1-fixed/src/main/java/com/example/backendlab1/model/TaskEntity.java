package com.example.backendlab1.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.Instant;

@Entity
@Table(name = "tasks")
public class TaskEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @Column(length = 2000)
    private String description;

    @Enumerated(EnumType.STRING)
    private PriorityLevel priorityLevel = PriorityLevel.MEDIUM;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private UserEntity owner;

    private Instant creationTimestamp = Instant.now();

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id;}
    public String getTitle() { return title;}
    public void setTitle(String title) { this.title = title;}
    public String getDescription() { return description;}
    public void setDescription(String description) { this.description = description;}
    public PriorityLevel getPriorityLevel() { return priorityLevel;}
    public void setPriorityLevel(PriorityLevel priorityLevel) { this.priorityLevel = priorityLevel;}
    public UserEntity getOwner() { return owner;}
    public void setOwner(UserEntity owner) { this.owner = owner;}
    public Instant getCreationTimestamp() { return creationTimestamp;}
    public void setCreationTimestamp(Instant creationTimestamp) { this.creationTimestamp = creationTimestamp;}
}
