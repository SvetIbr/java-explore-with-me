package ru.practicum.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.error.exception.*;

import java.time.LocalDateTime;

/**
 * Класс обработчика возникающих исключений
 *
 * @author Светлана Ибраева
 * @version 1.0
 */
@Slf4j
@RestControllerAdvice
public class ErrorHandler {
    /**
     * Метод обработки исключений при отсутствии искомых объектов
     *
     * @return ApiError {@link ApiError}
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundException(NotFoundException exception) {
        String reason = "The required object was not found.";
        log.error(exception.getMessage());
        return new ApiError(HttpStatus.NOT_FOUND.toString(), reason,
                exception.getMessage(), LocalDateTime.now());
    }

    /**
     * Метод обработки исключений при неподходящих входящих параметрах объекта
     *
     * @return ApiError {@link ApiError}
     */
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiError handleBadRequestException(BadRequestException exception) {
        String reason = "The required object was not found.";
        log.error(exception.getMessage());
        return new ApiError(HttpStatus.BAD_REQUEST.toString(), reason,
                exception.getMessage(), LocalDateTime.now());
    }

    /**
     * Метод обработки исключений при конфликте запроса с текущим состоянием сервера
     *
     * @return ApiError {@link ApiError}
     */
    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ApiError handleConflictException(ConflictException exception) {
        String reason = "For the requested operation the conditions are not met.";
        log.error(exception.getMessage());
        return new ApiError(HttpStatus.CONFLICT.toString(), reason,
                exception.getMessage(), LocalDateTime.now());
    }

    /**
     * Метод обработки исключений при попытке доступа к закрытым данным
     *
     * @return ApiError {@link ApiError}
     */
    @ExceptionHandler(AccessException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ApiError handleAccessException(AccessException exception) {
        String reason = "The required object was not found.";
        log.error(exception.getMessage());
        return new ApiError(HttpStatus.NOT_FOUND.toString(), reason,
                exception.getMessage(), LocalDateTime.now());
    }

    /**
     * Метод обработки исключений при нарушении уникальности имени категории
     *
     * @return ApiError {@link ApiError}
     */
    @ExceptionHandler(UniqueNameCategoryException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ApiError handleUniqueNameCategoryException(UniqueNameCategoryException exception) {
        String reason = "Integrity constraint has been violated.";
        log.error(exception.getMessage());
        return new ApiError(HttpStatus.CONFLICT.toString(), reason,
                exception.getMessage(), LocalDateTime.now());
    }

    /**
     * Метод обработки исключений при невалидных данных старта события
     *
     * @return ApiError {@link ApiError}
     */
    @ExceptionHandler(InvalidEventStartTimeException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ApiError handleInvalidEventStartTimeException(InvalidEventStartTimeException exception) {
        String reason = "For the requested operation the conditions are not met.";
        log.error(exception.getMessage());
        return new ApiError(HttpStatus.CONFLICT.toString(), reason,
                exception.getMessage(), LocalDateTime.now());
    }
}
