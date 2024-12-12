package crm.trainerhoursservice.service.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import crm.trainerhoursservice.model.TrainerWorkload;
import crm.trainerhoursservice.service.WorkloadService;
import jakarta.jms.Queue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageListener {

    private final WorkloadService workloadService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final JmsTemplate jmsTemplate;
    private final Queue deadLetterQueue = new ActiveMQQueue("workload.dead.letter.queue");

    @JmsListener(destination = "workload.queue")
    public void receiveMessage(String payload) {
        log.info("Got the message: {}", payload);
        objectMapper.registerModule(new JavaTimeModule());
        try {
            TrainerWorkload workload = objectMapper.readValue(payload, TrainerWorkload.class);
            workloadService.handleWorkload(workload);
        } catch (JsonProcessingException e) {
            log.error("Failed to deserialize message", e);
            jmsTemplate.convertAndSend(deadLetterQueue, payload);
        }
    }
}
