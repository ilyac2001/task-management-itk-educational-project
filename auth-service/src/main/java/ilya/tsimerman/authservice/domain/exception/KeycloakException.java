package ilya.tsimerman.authservice.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class KeycloakException extends RuntimeException {

    private final HttpStatusCode status;
    private final String responseBody;

    public KeycloakException(HttpStatusCode status, String responseBody) {
        super(extractMessage(responseBody));
        this.status = status;
        this.responseBody = responseBody;
    }

    private static String extractMessage(String body) {
        return body == null ? "Ошибка Keycloak" : body;
    }
}
