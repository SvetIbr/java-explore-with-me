package ru.practicum.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.service.UserService;

import javax.validation.Valid;
import java.util.List;

/**
 * Класс административного (для администраторов сервиса) контроллера для работы с сервисом пользователей
 *
 * @author Светлана Ибраева
 * @version 1.0
 */
@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(path = "/admin/users")
public class AdminUsersController {
    /**
     * Поле сервис для работы с хранилищем пользователей
     */
    private final UserService userService;

    /**
     * Метод получения информации о пользователях из хранилища сервиса через запрос
     *
     * @param ids  - список id пользователей
     * @param from - индекс первого элемента, начиная с 0
     * @param size - количество элементов для отображения
     * @return список EventFullDto {@link EventFullDto}
     */
    @GetMapping
    public List<UserDto> getUsers(@RequestParam(required = false) List<Long> ids,
                                  @RequestParam(required = false, defaultValue = "0") Integer from,
                                  @RequestParam(required = false, defaultValue = "10") Integer size) {
        log.info("GET: запрос на получение списка пользователей с параметрами: from = {}, size = {}," +
                "ids = {}", from, size, ids);
        return userService.getUsers(ids, PageRequest.of(from / size, size));
    }

    /**
     * Метод добавления пользователя в хранилище сервиса через запрос
     *
     * @param userDto {@link UserDto}
     * @return {@link UserDto} и код ответа API 201
     */
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        log.info("POST: создание пользователя с параметрами: {}", userDto);
        return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
    }

    /**
     * Метод удаления пользователя из хранилища сервиса по идентификатору через запрос
     *
     * @param userId - идентификатор пользователя
     */
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable Long userId) {
        log.info("DELETE: удаление пользователя по идентификатору {}", userId);
        userService.deleteUserById(userId);
    }
}
