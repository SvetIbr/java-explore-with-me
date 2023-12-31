package ru.practicum.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

import static ru.practicum.constants.Constants.DATE_TIME_FORMAT;

/**
 * Класс сведения об ошибке
 *
 * @author Светлана Ибраева
 * @version 1.0
 */
@Getter
@AllArgsConstructor
public class ApiError {
    /**
     * Поле код статуса HTTP-ответа
     */
    private String status;

    /**
     * Поле общее описание причины ошибки
     */
    private String reason;

    /**
     * Поле сообщение об ошибке
     */
    private String message;

    /**
     * Поле дата и время, когда произошла ошибка
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private LocalDateTime timestamp;
}

