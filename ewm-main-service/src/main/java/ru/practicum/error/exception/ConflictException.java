package ru.practicum.error.exception;

import org.springframework.http.HttpStatus;
import ru.practicum.error.ApiError;

public class ConflictException extends ApiError {
    private final HttpStatus status = HttpStatus.CONFLICT;

    public ConflictException(String message, String reason) {
        super(message, reason);
    }
}