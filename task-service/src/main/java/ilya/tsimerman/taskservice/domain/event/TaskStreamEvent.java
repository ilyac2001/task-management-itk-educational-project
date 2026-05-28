package ilya.tsimerman.taskservice.domain.event;

import ilya.tsimerman.taskservice.domain.data.common.TaskStatus;
import lombok.Builder;

@Builder
public record TaskStreamEvent(
        Long id,
        String title,
        String description,
        TaskStatus status,
        Long assigneeId
) {}
