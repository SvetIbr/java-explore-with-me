package ru.practicum.comment.dto;

import lombok.*;

import java.util.List;

/**
 * Класс объекта для блокировки комментариев со свойством <b>commentIdsForBlock</b>
 * для работы через REST-интерфейс
 *
 * @author Светлана Ибраева
 * @version 1.0
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlockCommentDto {
    /**
     * Поле список идентификаторов комментариев для блокировки
     */
    List<Long> commentIdsForBlock;
}
