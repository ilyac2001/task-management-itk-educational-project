package ilya.tsimerman.taskservice.domain.data.dto;

import ilya.tsimerman.taskservice.domain.data.common.TaskStatus;

public record TaskResponse(
        Long id,
        String title,
        String description,
        TaskStatus status,
        UserDto assignee
) {}
