package ru.practicum.user.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.user.dto.UserDto;

import java.util.List;

/**
 * Интерфейс сервиса пользователей
 *
 * @author Светлана Ибраева
 * @version 1.0
 */
public interface UserService {
    /**
     * Метод добавления пользователя в хранилище
     *
     * @param userDto {@link UserDto}
     * @return {@link UserDto} и код ответа API 201
     */
    UserDto createUser(UserDto userDto);

    /**
     * Метод удаления пользователя из хранилища по идентификатору
     *
     * @param userId - идентификатор пользователя
     */
    void deleteUserById(Long userId);

    /**
     * Метод получения информации о пользователях из хранилища
     *
     * @param ids - список id пользователей
     * @return список EventFullDto {@link EventFullDto}
     */
    List<UserDto> getUsers(List<Long> ids, Pageable pageable);
}
