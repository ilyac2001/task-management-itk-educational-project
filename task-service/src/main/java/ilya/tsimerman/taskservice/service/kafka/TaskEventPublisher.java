package ilya.tsimerman.taskservice.service.kafka;

import ilya.tsimerman.taskservice.domain.event.TaskCreatedFlowEvent;
import ilya.tsimerman.taskservice.domain.event.TaskStreamEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TaskEventPublisher {
    private static final String TASK_STREAM_TOPIC = "task-stream";
    private static final String TASK_FLOW_TOPIC = "task-flow";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publish(TaskCreatedFlowEvent event) {
        kafkaTemplate.send(TASK_STREAM_TOPIC, String.valueOf(event.taskId()), event);
    }

    public void publish(TaskStreamEvent event) {
        kafkaTemplate.send(TASK_FLOW_TOPIC, String.valueOf(event.id()), event);
    }
}
