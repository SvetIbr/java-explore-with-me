package ru.practicum;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Класс запроса по эндпоинту со свойствами <b>id</b>, <b>app</b>, <b>uri</b>,
 * <b>ip</b> и <b>timestamp</b> для работы через REST-интерфейс
 *
 * @author Светлана Ибраева
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HitDto {
    /**
     * Поле идентификатор записи
     */
    private Long id;

    /**
     * Поле название сервиса, для которого записывается информация
     */
    @NotNull
    @NotBlank
    private String app;

    /**
     * Поле URI, для которого был осуществлен запрос
     */
    @NotNull
    @NotBlank
    private String uri;

    /**
     * Поле IP-адрес пользователя, осуществившего запрос
     */
    @NotNull
    @NotBlank
    private String ip;

    /**
     * Поле дата и время, когда был совершен запрос к эндпоинту
     */
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
}