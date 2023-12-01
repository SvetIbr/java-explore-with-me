package ru.practicum.error.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import ru.practicum.error.ApiError;

@Getter
public class AccessException extends RuntimeException {

    public AccessException(String message) {
        super(message);
    }
}
