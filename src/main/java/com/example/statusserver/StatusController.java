package com.example.statusserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/status")
public class StatusController {

    private static final Logger logger = LoggerFactory.getLogger(StatusController.class);

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private ReplicationService replicationService;

    @PostMapping
    public Status setStatus(@RequestBody Status status) {
        logger.info("Setting status for user: {}", status.getUsername());
        Status savedStatus = statusRepository.save(status);
        replicationService.replicateStatus(savedStatus);
        return savedStatus;
    }

    @GetMapping("/{username}")
    public Status getStatus(@PathVariable String username) {
        logger.info("Getting status for user: {}", username);
        return statusRepository.findByUsername(username);
    }

    @DeleteMapping("/{username}")
    public void deleteStatus(@PathVariable String username) {
        logger.info("Deleting status for user: {}", username);
        statusRepository.deleteByUsername(username);
        replicationService.replicateDeletion(username);
    }

    @PutMapping("/{username}")
    public Status updateStatus(@PathVariable String username, @RequestBody Status status) {
        logger.info("Updating status for user: {}", username);
        Status existingStatus = statusRepository.findByUsername(username);
        if (existingStatus != null) {
            existingStatus.setStatustext
(status.getStatustext());
            existingStatus.setUhrzeit(status.getUhrzeit());
            statusRepository.save(existingStatus);
            replicationService.replicateStatus(existingStatus);
        }
        return existingStatus;
    }

    @GetMapping
    public Map<String, Status> getAllStatuses() {
        logger.info("Getting all statuses");
        return statusRepository.findAll();
    }
}