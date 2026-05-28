package ilya.tsimerman.authservice.Service.kafka;

import ilya.tsimerman.authservice.domain.event.UserCreatedFlowEvent;
import ilya.tsimerman.authservice.domain.event.UserStreamEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserEventListener {

    @KafkaListener(topics = "user-stream", groupId = "user-group")
    public void listenTaskStream(UserStreamEvent event) {
        log.info("UserStreamEvent: {}", event);
    }

    @KafkaListener(topics = "user-flow", groupId = "user-group")
    public void listenTaskFlow(UserCreatedFlowEvent event) {
        log.info("UserCreatedFlowEvent: {}", event);
    }
}