package ilya.tsimerman.authservice.domain.event;

public record UserStreamEvent(
        String id,
        String name
) {}
