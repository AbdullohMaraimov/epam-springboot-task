package crm.authservice.service.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DeadLetterConsumer {

    @JmsListener(destination = "auth.dead.letter.queue")
    public void consumeDeadLetter(String payload) {
        log.warn("Invalid request: {}", payload);
    }

}
