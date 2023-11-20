package ru.practicum.request.dto;

import lombok.*;

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
    private RequestStatus status;
}
