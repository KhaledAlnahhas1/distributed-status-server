package com.example.statusserver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class StatusRepositoryTest {

    private StatusRepository statusRepository;

    @BeforeEach
    public void setUp() {
        statusRepository = new StatusRepository();
    }

    @Test
    public void testSaveAndFindStatus() {
        Status status = new Status();
        status.setUsername("Max Mustermann");
        status.setStatustext("Im Unterricht");
        status.setUhrzeit(LocalDateTime.now());

        statusRepository.save(status);

        Status foundStatus = statusRepository.findByUsername("Max Mustermann");
        assertNotNull(foundStatus);
        assertEquals("Max Mustermann", foundStatus.getUsername());
        assertEquals("Im Unterricht", foundStatus.getStatustext());
    }

    @Test
    public void testDeleteStatus() {
        Status status = new Status();
        status.setUsername("Max Mustermann");
        status.setStatustext("Im Unterricht");
        status.setUhrzeit(LocalDateTime.now());

        statusRepository.save(status);
        statusRepository.deleteByUsername("Max Mustermann");

        Status foundStatus = statusRepository.findByUsername("Max Mustermann");
        assertNull(foundStatus);
    }

    @Test
    public void testFindAllStatuses() {
        Status status1 = new Status();
        status1.setUsername("Max Mustermann");
        status1.setStatustext("Im Unterricht");
        status1.setUhrzeit(LocalDateTime.now());

        Status status2 = new Status();
        status2.setUsername("Erika Mustermann");
        status2.setStatustext("Beim Sport");
        status2.setUhrzeit(LocalDateTime.now());

        statusRepository.save(status1);
        statusRepository.save(status2);

        assertEquals(2, statusRepository.findAll().size());
    }
}
