package ru.practicum.error.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import ru.practicum.error.ApiError;

@Getter
public class CategoryNotFoundException extends ApiError {
    private final HttpStatus status = HttpStatus.NOT_FOUND;

    public CategoryNotFoundException(String message, String reason) {
        super(message, reason);
    }
}
