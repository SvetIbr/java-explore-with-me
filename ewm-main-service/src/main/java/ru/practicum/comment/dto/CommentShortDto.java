package ru.practicum.comment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import ru.practicum.user.dto.UserShortDto;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentShortDto {
    private Long id;

    private String text;

    private UserShortDto author;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isChanged;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isBlocked;
}
