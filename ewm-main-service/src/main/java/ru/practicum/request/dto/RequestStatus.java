package ru.practicum.request.dto;

public enum RequestStatus { //Новый статус запроса на участие в событии текущего пользователя
    PENDING,
    CONFIRMED,
    REJECTED, // отклоненное
    CANCELED // отмененное
}
