package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import ru.practicum.event.enums.StateAction;
import ru.practicum.event.model.Location;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Future;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateEventDto {

    @Length(min = 20, max = 2000)
    private String annotation;

    private Long category;

    @Length(min = 20, max = 7000)
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Future
    private LocalDateTime eventDate;

    private Location location;

    @Column(columnDefinition = "boolean default false")
    private Boolean paid;

    @Column(columnDefinition = "integer default 0")
    private Integer participantLimit;

    @Column(columnDefinition = "boolean default true")
    private Boolean requestModeration;

    @Length(min = 3, max = 120)
    private String title;

    @Enumerated(EnumType.STRING)
    private StateAction stateAction;
}
