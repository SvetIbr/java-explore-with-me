package ru.practicum.error.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import ru.practicum.error.ApiError;

@Getter
public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
