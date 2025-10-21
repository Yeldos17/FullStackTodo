package com.example.backendlab1.controller;

import com.example.backendlab1.dto.CreateTaskRequest;
import com.example.backendlab1.model.AvailabilityStatus;
import com.example.backendlab1.model.PriorityLevel;
import com.example.backendlab1.repository.TaskRepository;
import com.example.backendlab1.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerTest {

    @Autowired private MockMvc mvc;
    @Autowired private TaskRepository taskRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private ObjectMapper mapper;

    @Test
    public void createTaskAndAssign() throws Exception {
        CreateTaskRequest r = new CreateTaskRequest();
        r.title = "unit-test-task";
        r.description = "desc";
        r.priorityLevel = PriorityLevel.LOW;

        mvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(r)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("unit-test-task"));
    }

    @Test
    public void assignNoAvailableShouldReturnConflict() throws Exception {
        CreateTaskRequest r = new CreateTaskRequest();
        r.title = "assign-test";
        r.description = "desc";

        MvcResult mvcResult = mvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(r)))
                .andExpect(status().isCreated())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        JsonNode node = mapper.readTree(body);
        Long taskId = node.get("id").asLong();

        userRepository.findAll().forEach(u -> {
            u.setAvailabilityStatus(AvailabilityStatus.BUSY);
            userRepository.save(u);
        });

        mvc.perform(post("/api/tasks/assign")
                        .param("taskId", taskId.toString()))
                .andExpect(status().isConflict());
    }
}
