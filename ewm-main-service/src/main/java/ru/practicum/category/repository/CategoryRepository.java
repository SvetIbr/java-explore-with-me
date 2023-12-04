package ru.practicum.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.category.model.Category;

/**
 * Интерфейс хранилища всех категорий
 *
 * @author Светлана Ибраева
 * @version 1.0
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
    /**
     * Метод проверки наличия в хранилище категории по наименованию
     *
     * @param name - наименование категории
     * @return true, если в хранилище есть категория с таким наименованием,
     *         false, если нет
     */
    boolean existsCategoryByName(String name);
}
