package com.example.statusserver;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StatusControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testSetAndGetStatus() {
        Status status = new Status();
        status.setUsername("Max Mustermann");
        status.setStatustext("Im Unterricht");
        status.setUhrzeit(LocalDateTime.now());

        ResponseEntity<Status> postResponse = restTemplate.postForEntity("http://localhost:" + port + "/status", status, Status.class);
        assertNotNull(postResponse.getBody());
        assertEquals("Max Mustermann", postResponse.getBody().getUsername());
        assertEquals("Im Unterricht", postResponse.getBody().getStatustext());

        ResponseEntity<Status> getResponse = restTemplate.getForEntity("http://localhost:" + port + "/status/Max Mustermann", Status.class);
        assertNotNull(getResponse.getBody());
        assertEquals("Max Mustermann", getResponse.getBody().getUsername());
        assertEquals("Im Unterricht", getResponse.getBody().getStatustext());
    }

    @Test
    public void testDeleteStatus() {
        Status status = new Status();
        status.setUsername("Max Mustermann");
        status.setStatustext("Im Unterricht");
        status.setUhrzeit(LocalDateTime.now());

        restTemplate.postForEntity("http://localhost:" + port + "/status", status, Status.class);
        restTemplate.delete("http://localhost:" + port + "/status/Max Mustermann");

        ResponseEntity<Status> getResponse = restTemplate.getForEntity("http://localhost:" + port + "/status/Max Mustermann", Status.class);
        assertNull(getResponse.getBody());
    }

    @Test
    public void testUpdateStatus() {
        Status status = new Status();
        status.setUsername("Max Mustermann");
        status.setStatustext("Im Unterricht");
        status.setUhrzeit(LocalDateTime.now());

        restTemplate.postForEntity("http://localhost:" + port + "/status", status, Status.class);

        Status updatedStatus = new Status();
        updatedStatus.setUsername("Max Mustermann");
        updatedStatus.setStatustext("Im Büro");
        updatedStatus.setUhrzeit(LocalDateTime.now());

        restTemplate.put("http://localhost:" + port + "/status/Max Mustermann", updatedStatus);

        ResponseEntity<Status> getResponse = restTemplate.getForEntity("http://localhost:" + port + "/status/Max Mustermann", Status.class);
        assertNotNull(getResponse.getBody());
        assertEquals("Max Mustermann", getResponse.getBody().getUsername());
        assertEquals("Im Büro", getResponse.getBody().getStatustext());
    }
}
