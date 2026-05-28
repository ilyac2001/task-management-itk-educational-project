package ilya.tsimerman.taskservice.service.kafka;

import ilya.tsimerman.taskservice.domain.event.TaskCreatedFlowEvent;
import ilya.tsimerman.taskservice.domain.event.TaskStreamEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TaskEventListener {

    @KafkaListener(topics = "task-stream", groupId = "task-group")
    public void listenTaskStream(TaskStreamEvent event) {
        log.info("TaskStreamEvent: {}", event);
    }

    @KafkaListener(topics = "task-flow", groupId = "task-group")
    public void listenTaskFlow(TaskCreatedFlowEvent event) {
        log.info("TaskCreatedFlowEvent: {}", event);
    }
}