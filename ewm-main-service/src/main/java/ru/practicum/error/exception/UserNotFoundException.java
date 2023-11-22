package ru.practicum.error.exception;

import org.springframework.http.HttpStatus;
import ru.practicum.error.ApiError;

public class UserNotFoundException extends ApiError {
    private final HttpStatus status = HttpStatus.NOT_FOUND;

    public UserNotFoundException(String message, String reason) {
        super(message, reason);
    }
}
