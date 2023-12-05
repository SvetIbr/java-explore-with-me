package ru.practicum.comment.dto;

import com.sun.istack.NotNull;
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
public class NewCommentDto {

    @NotNull
    @NotBlank
    @NotEmpty
    @Size(min = 5, max = 7000)
    private String text;
}
