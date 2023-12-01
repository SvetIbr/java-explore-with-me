package ru.practicum.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.request.dto.RequestStatus;
import ru.practicum.request.model.Request;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findAllByRequesterId(Long userId);

    Optional<Request> findByIdAndRequesterId(Long requestId, Long userId);

    List<Request> getAllByEventIdAndEventInitiatorId(Long eventId, Long userId);

    List<Request> findAllByIdInAndEventId(List<Long> requestIds, Long id);

    boolean existsByRequesterIdAndEventId(Long userId, Long eventId);

    Integer findCountByEventIdAndStatus(Long eventId, RequestStatus requestStatus);
}
