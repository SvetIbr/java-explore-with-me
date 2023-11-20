package ru.practicum.error.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import ru.practicum.error.ApiError;

@Getter
public class BadRequestException extends ApiError {
    private final HttpStatus status = HttpStatus.BAD_REQUEST;

    public BadRequestException(String message, String reason) {
        super(message, reason);
    }
}
