package ilya.tsimerman.domain.event;

import lombok.Builder;

@Builder
public record TaskEvent(
        Long taskId,
        TaskEventType eventType,
        Long assignee
) {}