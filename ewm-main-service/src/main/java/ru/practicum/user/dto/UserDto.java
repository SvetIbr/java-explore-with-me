package ru.practicum.user.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private Long id;

    @NotNull
    @NotEmpty
    @NotBlank
    @Length(min = 2, max = 250)
    private String name;

    @NotNull
    @Email
    @Length(min = 6, max = 254)
    private String email;
}
