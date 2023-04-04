package xyz.sorridi.stone.immutable;

/**
 * Error messages.
 * @author Sorridi
 * @since 1.0
 */
public enum ErrorMessages
{
    NULL("The value is null."),
    EMPTY("The value is empty."),
    POSITIVE("The value is positive."),
    NEGATIVE("The value is negative."),
    ZERO("The value is zero."),
    NOT_FOUND("The value is not found."),
    CONFIG_ERROR("The config is invalid."),
    NOT_IMPLEMENTED("The value is not implemented yet."),
    ARGS_NOT_SAME_SIZE("The arguments must have the same number of elements."),
    INVALID_ARRAY_LENGTH("The array length is invalid.");

    private final String message;

    ErrorMessages(String message)
    {
        this.message = message;
    }

    /**
     * Returns the error message.
     * @return The error message.
     */
    public String get()
    {
        return message;
    }

    /**
     * Returns the error message with the expect value.
      * @param arg The expected value.
     * @return The error message.
     */
    public String expect(Object arg)
    {
        return String.format(message + " Expected: %s", arg);
    }

}
