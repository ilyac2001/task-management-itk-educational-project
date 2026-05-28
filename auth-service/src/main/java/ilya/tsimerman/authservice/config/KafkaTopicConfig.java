package ilya.tsimerman.authservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@EnableKafka
public class KafkaTopicConfig {

    @Bean
    public NewTopic taskStreamTopic() {
        return TopicBuilder.name("user-stream")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic taskFlowTopic() {
        return TopicBuilder.name("user-flow")
                .partitions(1)
                .replicas(1)
                .build();
    }
}