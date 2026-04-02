package ilya.tsimerman.service.kafka;

import ilya.tsimerman.domain.event.TaskEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TaskEventListener {

    @KafkaListener(topics = "task-events", groupId = "task-group")
    public void listen(TaskEvent event) {
        log.info("Принято событие {}", event.toString());
    }
}