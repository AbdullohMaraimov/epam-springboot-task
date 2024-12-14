package crm.trainerhoursservice.config;

import jakarta.jms.Queue;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {

    @Value("${workload_dlq}")
    private String WORKLOAD_DLQ;

    @Bean
    public Queue deadLetterQueue() {
        return new ActiveMQQueue(WORKLOAD_DLQ);
    }

}
