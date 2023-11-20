package ru.practicum.compilation.dto;

import lombok.*;
import ru.practicum.event.dto.EventShortDto;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompilationDto {

    @NotNull
    private Long id;

    private List<EventShortDto> events;

    @NotNull
    private Boolean pinned;

    @NotNull
    private String title;
}
