package ru.practicum.error;

public class InvalidDateTimeException extends RuntimeException {
    public InvalidDateTimeException(String s) {
        super(s);
    }
}
