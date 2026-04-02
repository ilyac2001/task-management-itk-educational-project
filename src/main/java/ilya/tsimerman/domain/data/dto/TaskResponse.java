package ilya.tsimerman.domain.data.dto;

import ilya.tsimerman.domain.data.common.TaskStatus;

public record TaskResponse(
        Long id,
        String title,
        String description,
        TaskStatus status,
        UserDto assignee
) {}
