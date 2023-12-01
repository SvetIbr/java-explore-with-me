package ru.practicum.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.error.exception.ConflictException;
import ru.practicum.error.exception.NotFoundException;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.mapper.UserMapper;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto createUser(UserDto userDto) {
        User user = userMapper.toUser(userDto);
        User userToSave;
        try {
            userToSave = userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("User name and/or email already exists.");
        }
        return userMapper.toUserDto(userToSave);
    }

    public void deleteUserById(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(String
                    .format("User with id=%d was not found", userId));
        }
        userRepository.deleteById(userId);
    }

    public List<UserDto> getUsers(List<Long> ids, Pageable pageable) {
        Page<User> users;
        if (ids == null) {
            users = userRepository.findAll(pageable);
        } else {
            users = userRepository.findByIds(ids, pageable);
        }

        return users.stream()
                .map(userMapper::toUserDto)
                .collect(Collectors.toList());
    }
}
