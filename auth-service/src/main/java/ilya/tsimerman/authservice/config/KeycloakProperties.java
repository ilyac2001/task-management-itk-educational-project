package ilya.tsimerman.authservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "keycloak")
public record KeycloakProperties(
        String baseUrl,
        String realm,
        String adminUsername,
        String adminPassword,
        String clientId,
        String clientSecret
) {}
