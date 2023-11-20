package ru.practicum.user.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.user.dto.UserDto;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    public UserDto createUser(UserDto userDto) {

    }

    public void deleteUserById(Long userId) {

    }

    public List<UserDto> getUsers(List<Integer> ids, PageRequest of) {

    }

}
