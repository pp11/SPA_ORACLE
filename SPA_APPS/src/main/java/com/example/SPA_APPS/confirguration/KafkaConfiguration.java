package com.example.SPA_APPS.confirguration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfiguration {

    @Bean
    public NewTopic topic()
    {
        return TopicBuilder.name("master-topic")       // Topic name
                .partitions(2)                          // Number of partitions
                .replicas(1)                             // Replication factor
                .compact()                               // Optional: Enable log compaction
                .build();
    }
}
