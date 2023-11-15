package ru.practicum;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Класс просмотра статистики со свойствами <b>app</b>, <b>uri</b>, <b>views</b>
 * для работы через REST-интерфейс
 *
 * @author Светлана Ибраева
 * @version 1.0
 */
@Data
@AllArgsConstructor
@Builder
public class ViewStats {

    /**
     * Поле название сервиса
     */
    @NotBlank
    private String app;

    /**
     * Поле URI сервиса
     */
    @NotBlank
    private String uri;

    /**
     * Поле количество просмотров
     */
    @NotBlank
    @JsonProperty("hits")
    private Long views;
}
