package ru.practicum.request.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestDto {
    private Long id;
    private Long event;
    private String created;
    private Long requester;
    private String status;
}
