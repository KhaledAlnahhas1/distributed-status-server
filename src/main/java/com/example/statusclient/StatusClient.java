package com.example.statusclient;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Scanner;

public class StatusClient {
    private static final String BASE_URL = "http://localhost:8080/status";

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Set Status");
            System.out.println("2. Get Status");
            System.out.println("3. Delete Status");
            System.out.println("4. Update Status");
            System.out.println("5. Get All Statuses");
            System.out.println("6. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.println("Enter username:");
                    String username = scanner.nextLine();
                    System.out.println("Enter status text:");
                    String statusText = scanner.nextLine();
                    Status status = new Status(username, statusText, LocalDateTime.now());
                    ResponseEntity<Status> response = restTemplate.postForEntity(BASE_URL, status, Status.class);
                    System.out.println("Status set: " + response.getBody());
                    break;
                case 2:
                    System.out.println("Enter username:");
                    String getUsername = scanner.nextLine();
                    Status getStatus = restTemplate.getForObject(BASE_URL + "/" + getUsername, Status.class);
                    System.out.println("Status: " + getStatus);
                    break;
                case 3:
                    System.out.println("Enter username:");
                    String deleteUsername = scanner.nextLine();
                    restTemplate.delete(BASE_URL + "/" + deleteUsername);
                    System.out.println("Status deleted");
                    break;
                case 4:
                    System.out.println("Enter username:");
                    String updateUsername = scanner.nextLine();
                    System.out.println("Enter new status text:");
                    String updateStatusText = scanner.nextLine();
                    Status updateStatus = new Status(updateUsername, updateStatusText, LocalDateTime.now());
                    restTemplate.put(BASE_URL + "/" + updateUsername, updateStatus);
                    System.out.println("Status updated");
                    break;
                case 5:
                    Status[] statuses = restTemplate.getForObject(BASE_URL, Status[].class);
                    for (Status s : statuses) {
                        System.out.println(s);
                    }
                    break;
                case 6:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}

class Status {
    private String username;
    private String statustext;
    private LocalDateTime uhrzeit;

    public Status() {
    }

    public Status(String username, String statustext, LocalDateTime uhrzeit) {
        this.username = username;
        this.statustext = statustext;
        this.uhrzeit = uhrzeit;
    }

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

    @Override
    public String toString() {
        return "Status{" +
                "username='" + username + '\'' +
                ", statustext='" + statustext + '\'' +
                ", uhrzeit=" + uhrzeit +
                '}';
    }
}
