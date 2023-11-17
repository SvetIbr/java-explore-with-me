package ru.practicum.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;

/**
 * Класс обработчика возникающих исключений
 *
 * @author Светлана Ибраева
 * @version 1.0
 */
@RestControllerAdvice
public class ErrorHandler {
    /**
     * Метод обработки исключений при неудачном парсинге строки в объект LocalDateTime
     *
     * @return сообщение с описанием причины возникновения ошибки и статусом 400
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleDateTimeParseException(final DateTimeParseException e) {
        return new ErrorResponse(e.getMessage());
    }

    /**
     * Метод обработки исключений при ситуации, когда начало временного диапазона после конца диапазона
     *
     * @return сообщение с описанием причины возникновения ошибки и статусом 400
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidDateTimeException(final InvalidDateTimeException e) {
        return new ErrorResponse(e.getMessage());
    }
}
