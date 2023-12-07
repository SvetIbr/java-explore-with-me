package ru.practicum.comment.dto;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCommentDto {

    @NotNull
    @NotBlank
    @NotEmpty
    @Length(min = 5, max = 7000)
    private String text;
}
