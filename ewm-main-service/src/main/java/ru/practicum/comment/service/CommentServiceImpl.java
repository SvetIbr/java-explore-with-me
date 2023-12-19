package ru.practicum.comment.service;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.comment.dto.CommentDto;
import ru.practicum.comment.dto.NewCommentDto;
import ru.practicum.comment.dto.UpdateCommentDto;
import ru.practicum.comment.mapper.CommentMapper;
import ru.practicum.comment.model.Comment;
import ru.practicum.comment.model.QComment;
import ru.practicum.comment.repository.CommentRepository;
import ru.practicum.error.exception.BadRequestException;
import ru.practicum.error.exception.CommentBlockedException;
import ru.practicum.error.exception.ConflictException;
import ru.practicum.error.exception.NotFoundException;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.enums.EventState;
import ru.practicum.event.mapper.EventMappers;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.event.service.EventStatService;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.practicum.constants.Constants.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventMappers eventMapper;
    private final EventStatService eventStatService;

    public List<CommentDto> getAllCommentsByEvent(Long eventId) {
        checkEventInStorage(eventId);

        return commentRepository.findAllByEventId(eventId)
                .stream()
                .filter(comment -> comment.getIsBlocked() == null || !comment.getIsBlocked())
                .map(this::loadViewsAndCommentsToShortEventDto)
                .collect(Collectors.toList());
    }

    public List<CommentDto> searchCommentsForAdmin(String text, List<Long> users, List<Long> events,
                                                   boolean onlyUnlocked, boolean onlyBlocked,
                                                   LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                                   Pageable pageable) {
        if ((rangeStart != null && rangeEnd != null)
                && (rangeStart.isAfter(rangeEnd) || rangeStart.isEqual(rangeEnd))) {
            throw new BadRequestException(START_AFTER_END_MSG);
        }

        return getCommentsByParameters(text, users, events, onlyUnlocked, onlyBlocked,
                rangeStart, rangeEnd, pageable)
                .stream()
                .map(this::loadViewsAndCommentsToShortEventDto)
                .collect(Collectors.toList());
    }

    public List<CommentDto> blockComments(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }

        List<Comment> comments = commentRepository.findAllById(ids);
        for (Comment cur : comments) {
            if (cur.getIsBlocked() != null && cur.getIsBlocked()) {
                throw new ConflictException(COMMENT_ALREADY_BLOCKED_MSG);
            }
            cur.setIsBlocked(true);
            commentRepository.save(cur);
        }

        return comments.stream()
                .map(this::loadViewsAndCommentsToShortEventDto)
                .collect(Collectors.toList());
    }

    public CommentDto addComment(Long userId, Long eventId, NewCommentDto newCommentDto) {
        Event event = checkAndReturnEventInStorage(eventId);
        if (event.getState() != EventState.PUBLISHED) {
            throw new ConflictException(ONLY_FOR_PUBLISH_EVENT_MSG);
        }
        User author = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(String.format(USER_NOT_FOUND_MSG, userId)));

        Comment comment = commentMapper.toComment(newCommentDto);
        comment.setCreated(LocalDateTime.now());
        comment.setAuthor(author);
        comment.setEvent(event);
        comment = commentRepository.save(comment);

        return loadViewsAndCommentsToShortEventDto(comment);
    }

    public CommentDto updateComment(Long commentId, Long userId, Long eventId,
                                    UpdateCommentDto updateCommentDto) {
        checkEventInStorage(eventId);
        checkUserInStorage(userId);
        Comment comment = commentRepository.findByIdAndAuthorIdAndEventId(commentId, userId, eventId)
                .orElseThrow(() -> new NotFoundException(String.format(COMMENT_NOT_FOUND_MSG, commentId)));
        if (comment.getIsBlocked() != null && comment.getIsBlocked()) {
            throw new CommentBlockedException(COMMENT_BLOCK_MSG);
        }

        comment = commentMapper.updateComment(updateCommentDto, comment);
        comment.setIsChanged(true);
        comment.setCreated(LocalDateTime.now());
        comment = commentRepository.save(comment);
        return loadViewsAndCommentsToShortEventDto(comment);
    }

    private CommentDto loadViewsAndCommentsToShortEventDto(Comment comment) {
        CommentDto commentDto = commentMapper.toCommentDto(comment);
        EventShortDto eventShortDto = eventMapper.toEventShortDto(comment.getEvent());
        Map<Long, Long> views = eventStatService.getEventsViews(List.of(eventShortDto.getId()));
        eventShortDto.setViews(Math.toIntExact(views
                .getOrDefault(eventShortDto.getId(), 0L)));
        eventShortDto.setComments(commentRepository.findCountCommentByEventId(eventShortDto.getId()));
        commentDto.setEvent(eventShortDto);
        return commentDto;
    }

    public List<CommentDto> getAllCommentsForAuthorByEventId(Long userId, Long eventId) {
        checkEventInStorage(eventId);
        checkUserInStorage(userId);
        return commentRepository.findAllByEventIdAndAuthorId(eventId, userId).stream()
                .map(this::loadViewsAndCommentsToShortEventDto)
                .collect(Collectors.toList());
    }

    public List<CommentDto> getAllCommentsForAuthor(Long userId) {
        checkUserInStorage(userId);
        return commentRepository.findAllByAuthorId(userId).stream()
                .map(this::loadViewsAndCommentsToShortEventDto)
                .collect(Collectors.toList());
    }

    public CommentDto getComById(Long eventId, Long commentId) {
        checkEventInStorage(eventId);

        Comment comment = commentRepository.findByIdAndEventId(commentId, eventId)
                .orElseThrow(() -> new NotFoundException(String.format(COMMENT_NOT_FOUND_MSG, commentId)));

        if (comment.getIsBlocked() != null && comment.getIsBlocked()) {
            throw new CommentBlockedException(COMMENT_BLOCK_MSG);
        }

        return loadViewsAndCommentsToShortEventDto(comment);
    }

    public void deleteComById(Long userId, Long commentId) {
        checkUserInStorage(userId);

        if (!commentRepository.existsById(commentId)) {
            throw new NotFoundException(String.format(String.format(COMMENT_NOT_FOUND_MSG, commentId)));
        }

        commentRepository.findByIdAndAuthorId(commentId, userId)
                .orElseThrow(() -> new NotFoundException(NOT_RIGHTS_MSG));

        commentRepository.deleteById(commentId);
    }

    private void checkEventInStorage(Long eventId) {
        if (!eventRepository.existsById(eventId)) {
            throw new NotFoundException(String.format(EVENT_NOT_FOUND_MSG, eventId));
        }
    }

    private Event checkAndReturnEventInStorage(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(
                () -> new NotFoundException(String.format(EVENT_NOT_FOUND_MSG, eventId)));
    }

    private void checkUserInStorage(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(String.format(USER_NOT_FOUND_MSG, userId));
        }
    }

    private List<Comment> getCommentsByParameters(String searchText, List<Long> users,
                                                  List<Long> events, Boolean onlyUnlocked,
                                                  Boolean onlyBlocked, LocalDateTime rangeStart,
                                                  LocalDateTime rangeEnd, Pageable pageable) {
        QComment qComment = QComment.comment;
        BooleanBuilder predicate = new BooleanBuilder();

        if (searchText != null && !searchText.isEmpty()) {
            predicate.and(qComment.text.containsIgnoreCase(searchText));
        }

        if (users != null && !users.isEmpty()) {
            predicate.and(qComment.author.id.in(users));
        }

        if (events != null && !events.isEmpty()) {
            predicate.and(qComment.event.id.in(events));
        }

        if (rangeStart != null) {
            predicate.and(qComment.created.goe(rangeStart));
        }

        if (rangeEnd != null) {
            predicate.and(qComment.created.loe(rangeEnd));
        }

        if (onlyUnlocked != null) {
            if (onlyUnlocked) {
                predicate.and(qComment.isBlocked.isNull().or(qComment.isBlocked.isFalse()));
            }
        }

        if (onlyBlocked != null) {
            if (onlyBlocked) {
                predicate.and(qComment.isBlocked.isTrue());
            }
        }

        return commentRepository.findAll(predicate, pageable).toList();
    }
}
