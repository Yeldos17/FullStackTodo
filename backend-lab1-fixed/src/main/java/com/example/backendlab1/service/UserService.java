package com.example.backendlab1.service;

import com.example.backendlab1.model.UserEntity;
import com.example.backendlab1.model.AvailabilityStatus;
import com.example.backendlab1.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository repo) { this.userRepository = repo; }

    public UserEntity create(String username, String email, String rawPassword) {
        if (userRepository.existsByEmail(email)) throw new IllegalArgumentException("Email already used");
        UserEntity u = new UserEntity();
        u.setUsername(username);
        u.setEmail(email);
        u.setHashedPassword(passwordEncoder.encode(rawPassword));
        u.setAvailabilityStatus(AvailabilityStatus.AVAILABLE);
        return userRepository.save(u);
    }

    public Optional<UserEntity> findByEmail(String email) { return userRepository.findByEmail(email); }
    public List<UserEntity> findAvailableUsers() { return userRepository.findByAvailabilityStatus(AvailabilityStatus.AVAILABLE); }
    public List<UserEntity> findAll() { return userRepository.findAll(); }
    public Optional<UserEntity> findById(Long id) { return userRepository.findById(id); }
    public void delete(Long id) { userRepository.deleteById(id); }
}
