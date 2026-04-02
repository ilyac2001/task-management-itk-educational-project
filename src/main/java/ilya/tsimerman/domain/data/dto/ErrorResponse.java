package ilya.tsimerman.domain.data.dto;

import java.time.LocalDateTime;

public record ErrorResponse(
        String title,
        String detail,
        String request,
        LocalDateTime timestamp
) {}