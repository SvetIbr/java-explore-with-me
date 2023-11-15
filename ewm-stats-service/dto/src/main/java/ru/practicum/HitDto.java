package ru.practicum;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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
    @NotBlank
    @NotNull
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
    @JsonProperty("timestamp")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
}