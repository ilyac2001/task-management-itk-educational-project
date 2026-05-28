package ilya.tsimerman.authservice.Service.kafka;

import ilya.tsimerman.authservice.domain.event.UserCreatedFlowEvent;
import ilya.tsimerman.authservice.domain.event.UserStreamEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserEventPublisher {

    private static final String USER_STREAM_TOPIC = "user-stream";
    private static final String USER_FLOW_TOPIC = "user-flow";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publish(UserStreamEvent event) {
        kafkaTemplate.send(USER_STREAM_TOPIC, event.id(), event);
    }

    public void publish(UserCreatedFlowEvent event) {
        kafkaTemplate.send(USER_FLOW_TOPIC, event.userId(), event);
    }
}
