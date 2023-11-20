package ru.practicum.compilation.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewCompilationDto {

    private List<Long> events;


    @Column(columnDefinition = "boolean default false")
    private Boolean pinned;

    @NotNull
    @Length(min = 1, max = 50)
    private String title;
}
