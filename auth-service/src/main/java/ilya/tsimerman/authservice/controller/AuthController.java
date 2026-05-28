package ilya.tsimerman.authservice.controller;

import ilya.tsimerman.authservice.Service.AuthService;
import ilya.tsimerman.authservice.domain.dto.AuthLoginRequest;
import ilya.tsimerman.authservice.domain.dto.AuthRegisterRequest;
import ilya.tsimerman.authservice.domain.dto.KeycloakTokenResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/api/auth/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@Valid @RequestBody AuthRegisterRequest request) {
        authService.register(request);
    }

    @PostMapping(value = "/oauth/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<KeycloakTokenResponse> login(@ModelAttribute AuthLoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
