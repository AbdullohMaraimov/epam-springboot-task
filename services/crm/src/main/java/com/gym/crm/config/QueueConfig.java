package com.gym.crm.config;

import jakarta.jms.Queue;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {

    @Value("${workload.queue}")
    private String workloadQueueName;

    @Value("${auth.queue}")
    private String authQueueName;

    @Bean
    public Queue workloadQueue() {
        return new ActiveMQQueue(workloadQueueName);
    }

    @Bean
    public Queue authQueue() {
        return new ActiveMQQueue(authQueueName);
    }
}

