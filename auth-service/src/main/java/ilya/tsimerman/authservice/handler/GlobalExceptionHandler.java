package ilya.tsimerman.authservice.handler;

import ilya.tsimerman.authservice.domain.dto.ErrorResponse;
import ilya.tsimerman.authservice.domain.exception.KeycloakException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(KeycloakException.class)
    public ResponseEntity<ErrorResponse> handleKeycloak(KeycloakException ex,
                                                        HttpServletRequest request) {

        log.warn("Ошибка Keycloak: status={}, body={}",
                ex.getStatus(),
                ex.getResponseBody());

        return ResponseEntity.status(ex.getStatus())
                .body(buildErrorResponse(
                        "Ошибка Keycloak",
                        ex.getMessage(),
                        request
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex, HttpServletRequest request) {
        log.error("Внутренняя ошибка сервера: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildErrorResponse("Внутренняя ошибка сервера", ex.getMessage(), request));
    }

    private ErrorResponse buildErrorResponse(String title, String detail, HttpServletRequest request) {
        return new ErrorResponse(
                title,
                detail,
                String.format("%s %s", request.getMethod(), request.getRequestURI()),
                LocalDateTime.now()
        );
    }
}