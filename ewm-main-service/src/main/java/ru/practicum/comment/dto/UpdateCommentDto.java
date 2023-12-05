package ru.practicum.comment.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCommentDto {

    @Size(min = 5, max = 7000)
    @NotBlank
    @NotEmpty
    private String text;
}
