package crm.authservice.config;

import jakarta.jms.Queue;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {

    @Value("${auth_dlq}")
    String deadLetterQueueName;

    @Bean
    public Queue deadLetterQueue() {
        return new ActiveMQQueue(deadLetterQueueName);
    }

}
