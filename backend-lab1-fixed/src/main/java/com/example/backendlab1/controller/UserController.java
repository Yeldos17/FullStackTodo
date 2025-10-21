package com.example.backendlab1.controller;

import com.example.backendlab1.dto.CreateUserRequest;
import com.example.backendlab1.dto.UserDTO;
import com.example.backendlab1.mapper.EntityMapper;
import com.example.backendlab1.model.UserEntity;
import com.example.backendlab1.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) { this.userService = userService; }

    @PostMapping
    public ResponseEntity<UserDTO> create(@Valid @RequestBody CreateUserRequest req) {
        UserEntity u = userService.create(req.username, req.email, req.password);
        UserDTO dto = EntityMapper.toDTO(u);
        return ResponseEntity.created(URI.create("/api/users/" + u.getId())).body(dto);
    }

    @GetMapping
    public List<UserDTO> list() {
        return userService.findAll().stream().map(EntityMapper::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> get(@PathVariable Long id) {
        return userService.findById(id).map(u -> ResponseEntity.ok(EntityMapper.toDTO(u))).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
