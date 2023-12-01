package ru.practicum.error.exception;

public class InvalidEventStartTimeException extends RuntimeException {
    public InvalidEventStartTimeException(String message) {
        super(message);
    }
}
