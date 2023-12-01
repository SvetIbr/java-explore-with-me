package ru.practicum.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.error.exception.*;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundException(NotFoundException exception) {
        String reason = "The required object was not found.";
        log.error(exception.getMessage());
        return new ApiError(HttpStatus.NOT_FOUND.toString(), reason,
                exception.getMessage(), LocalDateTime.now());
    }


    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiError handleBadRequestException(BadRequestException exception) {
        String reason = "The required object was not found.";
        log.error(exception.getMessage());
        return new ApiError(HttpStatus.BAD_REQUEST.toString(), reason,
                exception.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ApiError handleConflictException(ConflictException exception) {
        String reason = "For the requested operation the conditions are not met.";
        log.error(exception.getMessage());
        return new ApiError(HttpStatus.CONFLICT.toString(), reason,
                exception.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(AccessException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ApiError handleAccessException(AccessException exception) {
        String reason = "The required object was not found.";
        log.error(exception.getMessage());
        return new ApiError(HttpStatus.NOT_FOUND.toString(), reason,
                exception.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(UniqueNameCategoryException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ApiError handleUniqueNameCategoryException(UniqueNameCategoryException exception) {
        String reason = "Integrity constraint has been violated.";
        log.error(exception.getMessage());
        return new ApiError(HttpStatus.CONFLICT.toString(), reason,
                exception.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(InvalidEventStartTimeException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ApiError handleInvalidEventStartTimeException(InvalidEventStartTimeException exception) {
        String reason = "For the requested operation the conditions are not met.";
        log.error(exception.getMessage());
        return new ApiError(HttpStatus.CONFLICT.toString(), reason,
                exception.getMessage(), LocalDateTime.now());
    }

}
