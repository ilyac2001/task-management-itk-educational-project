package ilya.tsimerman.authservice.domain.mapper;

import ilya.tsimerman.authservice.domain.dto.AuthRegisterRequest;
import ilya.tsimerman.authservice.domain.dto.CredentialDto;
import ilya.tsimerman.authservice.domain.dto.KeycloakRegisterRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface KeycloakUserMapper {

    @Mapping(source = "email", target = "username")
    @Mapping(target = "enabled", constant = "true")
    @Mapping(target = "credentials", expression = "java(toCredentials(request.password()))")
    KeycloakRegisterRequest toKeycloakRequest(AuthRegisterRequest request);

    default List<CredentialDto> toCredentials(String password) {
        return List.of(
                new CredentialDto("password", password, false)
        );
    }
}