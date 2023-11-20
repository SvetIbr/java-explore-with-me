package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.user.dto.UserShortDto;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventShortDto {
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
}
