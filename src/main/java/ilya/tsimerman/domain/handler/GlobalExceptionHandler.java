package ilya.tsimerman.domain.handler;

import ilya.tsimerman.domain.data.dto.ErrorResponse;
import ilya.tsimerman.domain.exception.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(EntityNotFoundException ex, HttpServletRequest request) {
        log.warn("Ресурс не найден: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildErrorResponse("Ресурс не найден", ex.getMessage(), request));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ResponseEntity<ErrorResponse> handleValidationErrors(Exception ex, HttpServletRequest request) {
        log.warn("Ошибка валидации запроса: {}", ex.getMessage());
        return ResponseEntity.badRequest()
                .body(buildErrorResponse("Ошибка валидации", ex.getMessage(), request));
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class,
            HttpMessageNotReadableException.class,
            ConversionFailedException.class})
    public ResponseEntity<ErrorResponse> handleConversionErrors(Exception ex, HttpServletRequest request) {
        log.warn("Некорректный формат запроса: {}", ex.getMessage());
        return ResponseEntity.badRequest()
                .body(buildErrorResponse("Ошибка формата запроса", ex.getMessage(), request));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotAllowed(
            HttpRequestMethodNotSupportedException ex,
            HttpServletRequest request
    ) {
        log.warn("Метод запроса не поддерживается: {}", ex.getMessage());

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(buildErrorResponse(
                        "Метод запроса не поддерживается",
                        String.format("HTTP %s не поддерживается для %s", ex.getMethod(), request.getRequestURI()),
                        request
                ));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundRequest(
            NoHandlerFoundException ex,
            HttpServletRequest request
    ) {
        log.warn("Ресурс не найден: {}", ex.getRequestURL());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildErrorResponse(
                        "Ресурс не найден",
                        String.format("URL %s не существует", ex.getRequestURL()),
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