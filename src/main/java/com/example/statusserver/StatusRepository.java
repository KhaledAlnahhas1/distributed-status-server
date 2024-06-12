package com.example.statusserver;

import org.springframework.stereotype.Repository;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Repository
public class StatusRepository {
    private final Map<String, Status> statusMap = new ConcurrentHashMap<>();

    public Status save(Status status) {
        statusMap.put(status.getUsername(), status);
        return status;
    }

    public Status findByUsername(String username) {
        return statusMap.get(username);
    }

    public void deleteByUsername(String username) {
        statusMap.remove(username);
    }

    public Map<String, Status> findAll() {
        return statusMap;
    }
}
