package ilya.tsimerman.authservice.Service.impl;

import ilya.tsimerman.authservice.Service.AuthService;
import ilya.tsimerman.authservice.Service.kafka.UserEventPublisher;
import ilya.tsimerman.authservice.config.KeycloakProperties;
import ilya.tsimerman.authservice.domain.dto.AuthLoginRequest;
import ilya.tsimerman.authservice.domain.dto.AuthRegisterRequest;
import ilya.tsimerman.authservice.domain.dto.KeycloakRegisterRequest;
import ilya.tsimerman.authservice.domain.dto.KeycloakTokenResponse;
import ilya.tsimerman.authservice.domain.event.UserCreatedFlowEvent;
import ilya.tsimerman.authservice.domain.event.UserStreamEvent;
import ilya.tsimerman.authservice.domain.exception.KeycloakException;
import ilya.tsimerman.authservice.domain.mapper.KeycloakUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final WebClient keycloakClient;
    private final KeycloakUserMapper keycloakUserMapper;
    private final KeycloakProperties properties;
    private final UserEventPublisher userEventPublisher;

    @Override
    public void register(AuthRegisterRequest userRequest) {

        String accessToken = getKeycloakAdminAccessToken();

        KeycloakRegisterRequest keycloakRequest = keycloakUserMapper.toKeycloakRequest(userRequest);

        ResponseEntity<Void> response = keycloakClient.post()
                .uri("/admin/realms/{realm}/users", properties.realm())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(keycloakRequest)
                .retrieve()
                .onStatus(HttpStatusCode::isError, res ->
                        res.bodyToMono(String.class)
                                .flatMap(body -> Mono.error(
                                        new KeycloakException(res.statusCode(), body)
                                ))
                )
                .toBodilessEntity()
                .block();

        String location = response.getHeaders().getFirst(HttpHeaders.LOCATION);
        String userId = location.substring(location.lastIndexOf("/") + 1);
        userEventPublisher.publish(
                new UserStreamEvent(userId, userRequest.name())
        );

        userEventPublisher.publish(
                new UserCreatedFlowEvent(userId, Instant.now())
        );
    }

    @Override
    public KeycloakTokenResponse login(AuthLoginRequest request) {
        return keycloakClient.post()
                .uri("/realms/{realm}/protocol/openid-connect/token", properties.realm())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("grant_type", "password")
                        .with("client_id", properties.clientId())
                        .with("client_secret", properties.clientSecret())
                        .with("username", request.email())
                        .with("password", request.password()))
                .retrieve()
                .onStatus(HttpStatusCode::isError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(body -> Mono.error(
                                        new KeycloakException(response.statusCode(), body)
                                ))
                )
                .bodyToMono(KeycloakTokenResponse.class)
                .block();
    }

    private String getKeycloakAdminAccessToken() {
        Map<String, Object> response =
                keycloakClient.post()
                        .uri("/realms/master/protocol/openid-connect/token")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .body(BodyInserters.fromFormData("grant_type", "password")
                                .with("client_id", "admin-cli")
                                .with("username", properties.adminUsername())
                                .with("password", properties.adminPassword()))
                        .retrieve()
                        .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                        .block();

        return (String) response.get("access_token");
    }
}