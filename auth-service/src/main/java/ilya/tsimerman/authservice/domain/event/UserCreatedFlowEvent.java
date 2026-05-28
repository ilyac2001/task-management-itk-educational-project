package ilya.tsimerman.authservice.domain.event;

import java.time.Instant;

public record UserCreatedFlowEvent(
        String userId,
        Instant createdAt
) {}
