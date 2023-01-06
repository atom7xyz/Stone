package xyz.sorridi.stone.immutable;

import lombok.Getter;

/**
 * Error messages.
 * @author Sorridi
 * @since 1.0
 */
@Getter
public enum ErrorMessages
{
    NULL("The value is null."),
    EMPTY("The value is empty."),
    POSITIVE("The value is positive."),
    NEGATIVE("The value is negative."),
    ZERO("The value is zero."),
    NOT_FOUND("The value is not found."),
    CONFIG_ERROR("The config is invalid."),
    NOT_IMPLEMENTED("The value is not implemented yet.");

    private final String message;

    ErrorMessages(String message)
    {
        this.message = message;
    }

    /**
     * Returns the error message with the expected value.
      * @param arg The expected value.
     * @return The error message.
     */
    public String expected(Object arg)
    {
        return String.format(message + " Expected: %s", arg);
    }

}
