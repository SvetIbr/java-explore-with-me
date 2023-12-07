package ru.practicum.comment.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlockCommentDto {
    List<Long> commentIdsForBlock;
}
