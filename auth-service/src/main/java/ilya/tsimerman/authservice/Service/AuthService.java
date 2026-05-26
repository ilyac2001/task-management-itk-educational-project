package ilya.tsimerman.authservice.Service;

import ilya.tsimerman.authservice.domain.dto.AuthRequest;

public interface AuthService {
    void register(AuthRequest request);

    //LoginResponse login(AuthRequest request);
}
