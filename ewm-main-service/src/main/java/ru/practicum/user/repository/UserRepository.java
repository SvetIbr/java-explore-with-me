package ru.practicum.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.user.model.User;

import java.util.List;

/**
 * Интерфейс хранилища всех пользователей
 *
 * @author Светлана Ибраева
 * @version 1.0
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Метод получения списка всех пользователей по списку идентификаторов
     *
     * @param ids - список идентификаторов искомых пользователей
     * @return список объектов User {@link User}
     */
    @Query("SELECT u " +
            "FROM User u " +
            "WHERE u.id in(?1) " +
            "ORDER BY u.id")
    Page<User> findByIds(List<Long> ids, Pageable pageable);
}
