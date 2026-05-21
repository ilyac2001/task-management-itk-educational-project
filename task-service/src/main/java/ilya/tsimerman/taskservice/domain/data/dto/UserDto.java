package ilya.tsimerman.taskservice.domain.data.dto;

public record UserDto(
        Long id,
        String name,
        String email
) {}
