package ilya.tsimerman.apigateway.filter;

import com.fasterxml.jackson.databind.JsonNode;
import ilya.tsimerman.apigateway.config.KeycloakProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static reactor.netty.http.HttpConnectionLiveness.log;

@Component
@RequiredArgsConstructor
public class UserInfoGatewayFilter implements GlobalFilter {

    private final WebClient keycloakClient;
    private final KeycloakProperties properties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String token = resolveToken(exchange);
        if (token == null) {
            return chain.filter(exchange);
        }

        return keycloakClient.get()
                .uri("/realms/{realm}/protocol/openid-connect/userinfo", properties.realm())
                .headers(h -> h.setBearerAuth(token))
                .retrieve()
                .bodyToMono(JsonNode.class)
                .flatMap(userInfo -> {

                    String userId = userInfo.get("sub").asText();

                    JsonNode rolesNode = userInfo
                            .path("realm_access")
                            .path("roles");

                    String roles = rolesNode.toString();

                    ServerHttpRequest mutatedRequest = exchange.getRequest()
                            .mutate()
                            .header("user-id", userId)
                            .header("user-roles", roles)
                            .build();
                    log.info("HEADERS для отправки: user-id={}, user-roles={}", userId, roles);
                    ServerWebExchange mutatedExchange = exchange
                            .mutate()
                            .request(mutatedRequest)
                            .build();

                    return chain.filter(mutatedExchange);
                });
    }

    private String resolveToken(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }

        return authHeader.substring(7);
    }
}
