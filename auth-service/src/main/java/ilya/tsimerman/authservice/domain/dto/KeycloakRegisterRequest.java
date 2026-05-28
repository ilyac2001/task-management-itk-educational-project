package ilya.tsimerman.authservice.domain.dto;

import java.util.List;

public record KeycloakRegisterRequest(
        String username,
        boolean enabled,
        List<CredentialDto> credentials
) {}
