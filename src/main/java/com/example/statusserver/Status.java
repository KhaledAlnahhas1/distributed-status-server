package com.example.statusserver;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Status implements Serializable {
    private String username;
    private String statustext;
    private LocalDateTime uhrzeit;

    // Getter und Setter

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatustext() {
        return statustext;
    }

    public void setStatustext(String statustext) {
        this.statustext = statustext;
    }

    public LocalDateTime getUhrzeit() {
        return uhrzeit;
    }

    public void setUhrzeit(LocalDateTime uhrzeit) {
        this.uhrzeit = uhrzeit;
    }
}
