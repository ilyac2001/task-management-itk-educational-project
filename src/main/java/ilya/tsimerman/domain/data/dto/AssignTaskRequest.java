package ilya.tsimerman.domain.data.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AssignTaskRequest(
        @NotNull(message = "ID исполнителя обязателен.")
        @Positive(message = "ID исполнителя должен быть положительным числом.")
        Long assigneeId
) {}
