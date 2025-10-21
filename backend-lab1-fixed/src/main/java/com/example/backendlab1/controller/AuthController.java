package com.example.backendlab1.controller;

import com.example.backendlab1.security.JwtUtil;
import com.example.backendlab1.model.UserEntity;
import com.example.backendlab1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(UserRepository userRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> userData) {
        String email = userData.get("email");
        String password = userData.get("password");

        Optional<UserEntity> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of("error", "User not found"));
        }

        UserEntity user = userOpt.get();

        // Если в БД пароли не захешированы — используй простое сравнение
        if (user.getHashedPassword().equals(password)) {
            String token = jwtUtil.generateToken(email);
            return ResponseEntity.ok(Map.of("token", token));
        }

        // Если пароли хранятся в хэше:
        // if (passwordEncoder.matches(password, user.getHashedPassword())) {
        //     String token = jwtUtil.generateToken(email);
        //     return ResponseEntity.ok(Map.of("token", token));
        // }

        return ResponseEntity.status(401).body(Map.of("error", "Invalid password"));
    }
}
