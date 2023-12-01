package ru.practicum.compilation.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.compilation.model.Compilation;

/**
 * Интерфейс хранилища всех подборок событий
 *
 * @author Светлана Ибраева
 * @version 1.0
 */
public interface CompilationRepository extends JpaRepository<Compilation, Long> {
    /**
     * Метод получения списка всех подборок с указанным параметром pinned
     *
     * @param pinned - закреплена ли подборка на главной странице сайта
     * @return список объектов Compilation {@link Compilation }
     */
    Page<Compilation> findAllByPinned(Boolean pinned, Pageable pageable);
}
