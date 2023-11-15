package ru.practicum.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Класс запроса к основному сервису со свойствами <b>id</b>, <b>uri</b>, <b>app</b>,
 * <b>ip</b> и <b>timestamp</b> для работы с хранилищем
 *
 * @author Светлана Ибраева
 * @version 1.0
 */
@Entity
@Table(name = "hits")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String app;
    private String uri;
    private String ip;
    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hit hit = (Hit) o;
        return Objects.equals(id, hit.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
