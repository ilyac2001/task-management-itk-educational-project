package ilya.tsimerman.taskservice.domain.data.mapper;

import ilya.tsimerman.taskservice.domain.data.dto.UserDto;
import ilya.tsimerman.taskservice.domain.data.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    UserDto toDto(User user);
}