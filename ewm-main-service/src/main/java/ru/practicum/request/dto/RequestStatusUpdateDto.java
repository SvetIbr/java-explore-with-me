package ru.practicum.request.dto;

import lombok.*;
import ru.practicum.event.enums.StatusForUpdate;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestStatusUpdateDto {
    @NotNull
    private List<Long> requestIds;
    @NotNull
    private StatusForUpdate status;
}
