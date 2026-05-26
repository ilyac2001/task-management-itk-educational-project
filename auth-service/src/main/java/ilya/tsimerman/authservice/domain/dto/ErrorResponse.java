package ilya.tsimerman.authservice.domain.dto;

import java.time.LocalDateTime;

public record ErrorResponse(
        String title,
        String detail,
        String request,
        LocalDateTime timestamp
) {}