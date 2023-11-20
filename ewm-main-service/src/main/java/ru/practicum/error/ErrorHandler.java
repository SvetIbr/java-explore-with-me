package ru.practicum.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.error.exception.BadRequestException;

import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestControllerAdvice
public class ErrorHandler {
    public static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleBadRequestException(final BadRequestException e) {
        return new ResponseEntity<>((Map.of(
                "error", e.getMessage(),
                "reason", e.getReason(),
                "timestamp", e.getTimeStamp().format(FORMAT),
                "status", e.getStatus())),
                HttpStatus.BAD_REQUEST);
    }
}
