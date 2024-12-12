package crm.trainerhoursservice.service.mq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DQLConsumer {

    @JmsListener(destination = "${workload_dlq}")
    public void deadLetterConsume(String payload) {
        log.warn("Invalid message: {}", payload);
    }

}
