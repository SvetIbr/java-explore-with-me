package ru.practicum.error.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import ru.practicum.error.ApiError;

@Getter
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
