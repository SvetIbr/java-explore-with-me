package ru.practicum.event.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateEventDto extends NewEventDto {
    private StateAction stateAction;
}
