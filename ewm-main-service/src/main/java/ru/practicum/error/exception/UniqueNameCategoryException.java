package ru.practicum.error.exception;

import org.springframework.http.HttpStatus;
import ru.practicum.error.ApiError;

public class UniqueNameCategoryException extends ApiError {
    private final HttpStatus status = HttpStatus.BAD_REQUEST;

    public UniqueNameCategoryException(String message, String reason) {
        super(message, reason);
    }

    }

