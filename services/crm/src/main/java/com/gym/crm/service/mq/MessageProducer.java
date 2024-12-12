package com.gym.crm.service.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gym.crm.model.dto.request.RegisterRequest;
import com.gym.crm.model.dto.request.TrainerWorkload;
import jakarta.jms.Queue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageProducer {

    private final ObjectMapper objectMapper;
    private final JmsTemplate jmsTemplate;
    private final Queue workloadQueue;
    private final Queue authQueue;


    public void sendMessage(TrainerWorkload trainerWorkload) throws JsonProcessingException {
        log.info("Sending to workload queue: {}", trainerWorkload);
        objectMapper.registerModule(new JavaTimeModule());
        String jsonPayload = objectMapper.writeValueAsString(trainerWorkload);
        jmsTemplate.convertAndSend(workloadQueue, jsonPayload);
    }

    public void sendMessage(RegisterRequest registerRequest) throws JsonProcessingException {
        log.info("Sending to auth queue: {}", registerRequest);
        objectMapper.registerModule(new JavaTimeModule());
        String jsonPayload = objectMapper.writeValueAsString(registerRequest);
        jmsTemplate.convertAndSend(authQueue, jsonPayload);
    }

}
