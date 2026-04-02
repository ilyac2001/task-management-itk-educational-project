package ilya.tsimerman.domain.data.mapper;

import ilya.tsimerman.domain.data.dto.UserDto;
import ilya.tsimerman.domain.data.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    UserDto toDto(User user);
}