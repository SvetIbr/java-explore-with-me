package ru.practicum.comment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.user.dto.UserShortDto;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {
    private Long id;

    private String text;

    private UserShortDto author;

    private EventShortDto event;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean isChanged;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean isBlocked;
}
