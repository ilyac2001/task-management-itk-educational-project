package ilya.tsimerman.service.kafka;

import ilya.tsimerman.domain.event.TaskEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TaskEventPublisher {

    private static final String TOPIC = "task-events";

    private final KafkaTemplate<String, TaskEvent> kafkaTemplate;

    public void publish(TaskEvent event) {
        kafkaTemplate.send(TOPIC, String.valueOf(event.taskId()), event);
    }
}