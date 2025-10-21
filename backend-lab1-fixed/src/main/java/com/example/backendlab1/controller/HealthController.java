package com.example.backendlab1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Главная страница
    @GetMapping("/")
    public String home() {
        return "Welcome to backend-lab1 API!";
    }

    // Проверка подключения к базе данных
    @GetMapping("/health")
    public String health() {
        try {
            jdbcTemplate.execute("SELECT 1");
            return "OK";
        } catch (Exception e) {
            return "DB_ERROR";
        }
    }
}
