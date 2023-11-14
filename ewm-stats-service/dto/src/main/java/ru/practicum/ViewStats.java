package ru.practicum;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@Builder
public class ViewStats {

    @NotBlank
    private String app;

    @NotBlank
    private String uri;

    @NotBlank
    @JsonProperty("hits")
    private Long views;
}
