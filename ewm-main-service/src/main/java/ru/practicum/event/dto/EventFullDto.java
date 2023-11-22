package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.aspectj.internal.lang.annotation.ajcDeclareAnnotation;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.event.model.EventStatus;
import ru.practicum.location.dto.LocationDto;
import ru.practicum.user.dto.UserShortDto;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventFullDto {
    private Long id;

    @NotNull
    protected String annotation;

    @NotNull
    protected CategoryDto category;

    protected Integer confirmedRequests;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    protected String eventDate;

    @NotNull
    protected UserShortDto initiator;

    @NotNull
    protected Boolean paid;

    @NotNull
    protected String title;

    protected Integer views;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private String createdOn;

    private String description;

    @NotNull
    private LocationDto location;

    @Column(columnDefinition = "integer default 0")
    private Integer participantLimit;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private String publishedOn;

    @Column(columnDefinition = "boolean default true")
    private Boolean requestModeration;

    private EventStatus state;
}
