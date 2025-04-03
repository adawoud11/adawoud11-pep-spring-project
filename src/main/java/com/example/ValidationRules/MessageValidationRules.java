package com.example.ValidationRules;

public final class MessageValidationRules {

    /**
     * Validates the conditions for creating a new message.
     * Throws ValidationException if any of following condition is not met:
     * If message_text is blank
     * exceeds 255 characters
     * posted_by does not exist
     * message_id does not exist
     */

    // Constants using final static for error messages and configuration
    public static final String MESSAGE_IS_NULL_ERROR = "Message Object can not be Null";
    public static final String MESSAGE_TEXT_BLANK_ERROR = "Message text cannot be blank";
    public static final String MESSAGE_TEXT_LENGTH_ERROR = "Message text must be 255 characters or less";
    public static final String USER_NOT_FOUND_ERROR = "Posted_by must refer to an existing user";
    public static final String MESSAGE_NOT_FOUND_ERROR = "Message ID does not exist";
    public static final int MAX_MESSAGE_LENGTH = 255;

}