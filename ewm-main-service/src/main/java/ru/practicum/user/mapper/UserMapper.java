package ru.practicum.user.mapper;

import org.mapstruct.Mapper;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.dto.UserShortDto;
import ru.practicum.user.model.User;

/**
 * Mapper-класс для преобразования объектов сервиса пользователей
 *
 * @author Светлана Ибраева
 * @version 1.0
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
    /**
     * Метод преобразования объекта User в UserShortDto
     *
     * @param user {@link User}
     * @return {@link UserShortDto}
     */
    UserShortDto toUserShortDto(User user);

    /**
     * Метод преобразования объекта UserDto в User
     *
     * @param userDto {@link UserDto}
     * @return {@link User}
     */
    User toUser(UserDto userDto);

    /**
     * Метод преобразования объекта User в UserDto
     *
     * @param user {@link User}
     * @return {@link UserDto}
     */
    UserDto toUserDto(User user);
}
