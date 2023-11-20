package ru.practicum.user.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserShortDto {

    @NotNull
    private Long id;

    @NotNull
    private String name;
}
