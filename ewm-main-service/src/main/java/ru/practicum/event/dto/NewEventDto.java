package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import ru.practicum.location.dto.LocationDto;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewEventDto {
    @NotNull
    @Length(min = 20, max = 2000)
    protected String annotation;

    @NotNull
    protected Long category;

    @NotNull
    @Length(min = 20, max = 7000)
    protected String description;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    protected String eventDate;

    @NotNull
    protected LocationDto location;

    @Column(columnDefinition = "boolean default false")
    protected Boolean paid;

    @Column(columnDefinition = "integer default 0")
    protected Integer participantLimit;

    @Column(columnDefinition = "boolean default true")
    protected Boolean requestModeration;

    @NotNull
    @Length(min = 3, max = 120)
    protected String title;
}
