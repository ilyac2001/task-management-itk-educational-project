package ilya.tsimerman.taskservice.domain.event;

import lombok.Builder;

import java.time.Instant;

@Builder
public record TaskCreatedFlowEvent(
        Long taskId,
        Instant createdAt
) {}
