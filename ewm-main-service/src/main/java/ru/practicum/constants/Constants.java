package ru.practicum.constants;

import java.time.format.DateTimeFormatter;

public class Constants {
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
    public static final String APP_CODE = "ewm-main-service";
    public static final String URI = "/events/";
    public static final int HOUR_BEFORE_EVENT_DATE = 1;
    public static final int HOURS_BEFORE_EVENT_DATE = 2;
    public static final String CATEGORY_NOT_FOUND_MSG = "Category with id=%d was not found";
    public static final String CATEGORY_HAVE_EVENTS_MSG = "Category must not be assigned to any event";
    public static final String CATEGORY_NOT_UNIQUE_NAME_MSG = "Category name %s already exists";
    public static final String COMPILATION_NOT_FOUND_MSG = "Compilation with id=%d was not found";
    public static final String REQUEST_NOT_FOUND_MSG = "Request with id=%d was not found";
    public static final String INCORRECT_SIZE_IDS_MSG =
            "Incorrect request id(s) received in the request body";
    public static final String INCORRECT_STATUS_MSG =
            "Request must have status PENDING";
    public static final String LIMIT_IS_OVER_MSG = "The participant limit has been reached";
    public static final String EVENT_NOT_FOUND_MSG = "Event with id=%d was not found";
    public static final String USER_NOT_FOUND_MSG = "User with id=%d was not found";
    public static final String REQUEST_ALREADY_EXISTS_MSG =
            "The request for user's participation in tne event id=%d already exists";
    public static final String INCORRECT_REQUEST_MSG =
            "User cannot send participation request in self-published event";
    public static final String REQUEST_FOR_NOT_PUBLIC_EVENT_MSG =
            "Request can be made only for published events";
    public static final String USER_NOT_UNIQUE_NAME_MSG = "User name and/or email already exists";
    public static final String START_AFTER_END_MSG = "Start time must not after or equal to end time";
    public static final String UNEXPECTED_VALUE_MSG = "Unexpected value: %s";
    public static final String INCORRECT_EVENT_DATE_MSG =
            "Field: eventDate. " +
                    "Error: должно содержать дату, которая еще не наступила. " +
                    "Value: %s";
    public static final String START_EVENT_DATE_IN_LESS_THAN_TWO_HOURS_MSG =
            "You can edit an event no later than two hours before the start";
    public static final String NOT_FOR_PUBLISH_EVENT_MSG =
            "Event must not be published";
    public static final String UNAVAILABLE_EVENT_MSG = "You don't have enough rights to edit the event";
    public static final String START_EVENT_DATE_IN_LESS_THAN_ONE_HOUR_ADMIN_MSG =
            "You can edit an event no later than one hour before the start";
    public static final String NOT_PUBLIC_EVENT_MSG = "Event should not be published";
    public static final String NOT_FOUND_REASON = "The required object was not found";
    public static final String BAD_REQUEST_REASON = "Incorrectly made request";
    public static final String CONFLICT_REASON = "For the requested operation the conditions are not met";
    public static final String CATEGORY_NOT_UNIQUE_NAME_REASON =
            "Integrity constraint has been violated";

    public static final String COMMENT_NOT_FOUND_MSG = "Comment with id=%d was not found";
    public static final String COMMENT_ALREADY_BLOCKED_MSG = "Comment is already blocked";

    public static final String ONLY_FOR_PUBLISH_EVENT_MSG =
            "Event must be published";

    public static final String COMMENT_BLOCK_MSG =
            "The comment was blocked";
}
