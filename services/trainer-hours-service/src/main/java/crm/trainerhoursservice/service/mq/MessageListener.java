package crm.trainerhoursservice.service.mq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import crm.trainerhoursservice.model.dto.TrainerWorkload;
import crm.trainerhoursservice.service.WorkloadService;
import jakarta.jms.Queue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageListener {

    private final WorkloadService workloadService;
    private final ObjectMapper objectMapper;
    private final JmsTemplate jmsTemplate;
    private final Queue deadLetterQueue;

    @JmsListener(destination = "${workload_queue}")
    public void receiveMessage(String payload) {
        log.info("Got the message: {}", payload);
        try {
            TrainerWorkload workload = objectMapper.readValue(payload, TrainerWorkload.class);
            workloadService.handleWorkload(workload);
        } catch (JsonProcessingException e) {
            log.error("Failed to deserialize message", e);
            jmsTemplate.convertAndSend(deadLetterQueue, payload);
        }
    }
}
