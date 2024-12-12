package crm.authservice.service.mq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import crm.authservice.model.dto.RegisterRequest;
import crm.authservice.service.AuthService;
import jakarta.jms.Queue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageConsumer {

    private final AuthService authService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final JmsTemplate jmsTemplate;
    private final Queue deadLetterQueue = new ActiveMQQueue("auth.dead.letter.queue");

    @JmsListener(destination = "auth.queue")
    public void receiveMessage(String payload) {
        objectMapper.registerModule(new JavaTimeModule());
        try {
            RegisterRequest registerRequest = objectMapper.readValue(payload, RegisterRequest.class);
            authService.register(registerRequest);
        } catch (IOException e) {
            log.error("Failed to deserialize message", e);
            jmsTemplate.convertAndSend(deadLetterQueue, payload);
        }
    }

}
