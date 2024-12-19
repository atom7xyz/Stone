package xyz.sorridi.stone.common.immutable;

/**
 * Error messages.
 *
 * @author atom7xyz
 * @since 1.0
 */
public enum Err
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
    INVALID_ARRAY_LENGTH("The array length is invalid."),

    MUST_BE_POSITIVE("The value must be positive."),
    MUST_BE_NEGATIVE("The value must be negative."),
    MUST_BE_ZERO("The value must be zero."),
    MUST_BE_ZERO_OR_POSITIVE("The value must be zero or positive."),
    MUST_BE_ZERO_OR_NEGATIVE("The value must be zero or negative."),
    MUST_BE_NULL("The value must be null."),
    MUST_BE_EMPTY("The value must be empty."),
    MUST_BE_NOT_NULL("The value must not be null."),
    MUST_BE_NOT_EMPTY("The value must not be empty.");

    private final String message;

    Err(String message)
    {
        this.message = message;
    }

    /**
     * Returns the error message.
     *
     * @return The error message.
     */
    public String get()
    {
        return message;
    }

    /**
     * Returns the error message with the expect value.
     *
     * @param args The expected values.
     * @return The error message.
     */
    public String expect(Object... args)
    {
        int length = args.length;

        if (length == 0)
        {
            return message;
        }

        StringBuilder builder = new StringBuilder(message + " Expected: ");

        for (int i = 0; i < length; i++)
        {
            var arg = args[i];

            if (arg == null)
            {
                continue;
            }

            builder.append(arg);

            if (i != length - 1)
            {
                builder.append(", ");
            }
        }

        return builder.toString();
    }

}
