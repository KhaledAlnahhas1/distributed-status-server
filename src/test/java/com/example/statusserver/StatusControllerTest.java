package com.example.statusserver;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class StatusControllerTest {

    private MockMvc mockMvc;

    @Mock
    private StatusRepository statusRepository;

    @Mock
    private ReplicationService replicationService;

    @InjectMocks
    private StatusController statusController;

    @Test
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(statusController).build();
    }

    @Test
    public void testSetStatus() throws Exception {
        Status status = new Status();
        status.setUsername("Max Mustermann");
        status.setStatustext("Im Unterricht");
        status.setUhrzeit(LocalDateTime.now());

        when(statusRepository.save(any(Status.class))).thenReturn(status);

        mockMvc.perform(post("/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"Max Mustermann\", \"statustext\": \"Im Unterricht\", \"uhrzeit\": \"2022-01-03T13:30:00\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("Max Mustermann"))
                .andExpect(jsonPath("$.statustext").value("Im Unterricht"));
    }

    @Test
    public void testGetStatus() throws Exception {
        Status status = new Status();
        status.setUsername("Max Mustermann");
        status.setStatustext("Im Unterricht");
        status.setUhrzeit(LocalDateTime.now());

        when(statusRepository.findByUsername("Max Mustermann")).thenReturn(status);

        mockMvc.perform(get("/status/Max Mustermann")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("Max Mustermann"))
                .andExpect(jsonPath("$.statustext").value("Im Unterricht"));
    }

    @Test
    public void testDeleteStatus() throws Exception {
        doNothing().when(statusRepository).deleteByUsername("Max Mustermann");

        mockMvc.perform(delete("/status/Max Mustermann")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateStatus() throws Exception {
        Status status = new Status();
        status.setUsername("Max Mustermann");
        status.setStatustext("Im Unterricht");
        status.setUhrzeit(LocalDateTime.now());

        when(statusRepository.findByUsername("Max Mustermann")).thenReturn(status);
        when(statusRepository.save(any(Status.class))).thenReturn(status);

        mockMvc.perform(put("/status/Max Mustermann")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"Max Mustermann\", \"statustext\": \"Im Büro\", \"uhrzeit\": \"2022-01-03T13:30:00\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("Max Mustermann"))
                .andExpect(jsonPath("$.statustext").value("Im Büro"));
    }
}
