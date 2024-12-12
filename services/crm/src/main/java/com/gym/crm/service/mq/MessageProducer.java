package com.gym.crm.service.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gym.crm.model.dto.request.RegisterRequest;
import com.gym.crm.model.dto.request.TrainerWorkload;
import jakarta.jms.Queue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageProducer {

    private final JmsTemplate jmsTemplate;
    private final Queue workloadQueue = new ActiveMQQueue("workload.queue");
    private final Queue authQueue = new ActiveMQQueue("auth.queue");


    public void sendMessage(TrainerWorkload trainerWorkload) throws JsonProcessingException {
        log.info("Sending to workload queue: {}", trainerWorkload);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String jsonPayload = objectMapper.writeValueAsString(trainerWorkload);
        jmsTemplate.convertAndSend(workloadQueue, jsonPayload);
    }

    public void sendMessage(RegisterRequest registerRequest) throws JsonProcessingException {
        log.info("Sending to auth queue: {}", registerRequest);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String jsonPayload = objectMapper.writeValueAsString(registerRequest);
        jmsTemplate.convertAndSend(authQueue, jsonPayload);
    }

}
