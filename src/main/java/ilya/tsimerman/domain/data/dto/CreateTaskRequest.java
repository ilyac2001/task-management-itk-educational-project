package ilya.tsimerman.domain.data.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateTaskRequest(
        @NotBlank(message = "Название задачи не должно быть пустым.")
        @Size(max = 255, message = "Название задачи не должно превышать 255 символов.")
        String title,

        @Size(max = 5000, message = "Описание задачи не должно превышать 5000 символов.")
        String description
) {}
