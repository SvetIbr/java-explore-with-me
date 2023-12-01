package ru.practicum.user.mapper;

import org.mapstruct.Mapper;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.dto.UserShortDto;
import ru.practicum.user.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserShortDto toUserShortDto(User user);

    User toUser(UserDto userDto);

    UserDto toUserDto(User user);
}
