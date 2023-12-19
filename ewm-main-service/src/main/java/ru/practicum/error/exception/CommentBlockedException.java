package ru.practicum.error.exception;

import lombok.Getter;

@Getter
public class CommentBlockedException extends RuntimeException {
    public CommentBlockedException(String message) {
        super(message);
    }
}
