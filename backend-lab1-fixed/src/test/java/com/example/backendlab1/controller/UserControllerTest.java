package com.example.backendlab1.controller;

import com.example.backendlab1.dto.CreateUserRequest;
import com.example.backendlab1.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserControllerTest {
    @Autowired MockMvc mvc;
    @Autowired UserRepository userRepository;
    @Autowired ObjectMapper mapper;

    @BeforeEach
    void cleanDb() {
        userRepository.deleteAll();
    }

    @Test
    public void createAndGetUser() throws Exception {
        CreateUserRequest r = new CreateUserRequest();
        r.username = "testuser";
        r.email = "uniqueuser@example.com"; // 👈 уникальный e-mail
        r.password = "secret";

        mvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(r)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("uniqueuser@example.com"));

        // ensure duplicate email rejected
        mvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(r)))
                .andExpect(status().isBadRequest());
    }
}
