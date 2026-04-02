package ilya.tsimerman.domain.data.dto;

import ilya.tsimerman.domain.data.common.TaskStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateTaskStatusRequest(
        @NotNull(message = "Статус обязателен.")
        TaskStatus status
) {}
