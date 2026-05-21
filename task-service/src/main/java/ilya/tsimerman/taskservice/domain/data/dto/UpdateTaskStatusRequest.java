package ilya.tsimerman.taskservice.domain.data.dto;

import ilya.tsimerman.taskservice.domain.data.common.TaskStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateTaskStatusRequest(
        @NotNull(message = "Статус обязателен.")
        TaskStatus status
) {}
