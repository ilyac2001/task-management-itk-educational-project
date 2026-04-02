package ilya.tsimerman.domain.data.mapper;

import ilya.tsimerman.domain.data.dto.CreateTaskRequest;
import ilya.tsimerman.domain.data.dto.TaskResponse;
import ilya.tsimerman.domain.data.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class})
public interface TaskMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "CREATED")
    @Mapping(target = "assignee", ignore = true)
    Task toEntity(CreateTaskRequest requestDto);

    TaskResponse toDto(Task entity);
}
