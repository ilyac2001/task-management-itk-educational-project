package ilya.tsimerman.apigateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {

    private final KeycloakProperties properties;

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder
                .baseUrl(properties.baseUrl())
                .build();
    }
}
