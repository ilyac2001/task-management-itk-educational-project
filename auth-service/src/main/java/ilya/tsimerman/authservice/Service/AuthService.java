package ilya.tsimerman.authservice.Service;

import ilya.tsimerman.authservice.domain.dto.AuthLoginRequest;
import ilya.tsimerman.authservice.domain.dto.AuthRegisterRequest;
import ilya.tsimerman.authservice.domain.dto.KeycloakTokenResponse;

public interface AuthService {
    void register(AuthRegisterRequest request);

    KeycloakTokenResponse login(AuthLoginRequest request);
}
