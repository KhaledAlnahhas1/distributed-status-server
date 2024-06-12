package com.example.statusserver;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReplicationService {

    private static final String EXCHANGE_NAME = "statusExchange";
    private static final String ROUTING_KEY = "statusKey";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private StatusRepository statusRepository;

    public void replicateStatus(Status status) {
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, status);
    }

    public void replicateDeletion(String username) {
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, username + "_delete");
    }

    @RabbitListener(queues = "#{queue.name}")
    public void receiveMessage(Object message) {
        if (message instanceof Status) {
            statusRepository.save((Status) message);
        } else if (message instanceof String && ((String) message).endsWith("_delete")) {
            String username = ((String) message).replace("_delete", "");
            statusRepository.deleteByUsername(username);
        }
    }
}
