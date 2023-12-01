package ru.practicum.user.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.user.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDto);

    void deleteUserById(Long userId);

    List<UserDto> getUsers(List<Long> ids, Pageable pageable);
}
