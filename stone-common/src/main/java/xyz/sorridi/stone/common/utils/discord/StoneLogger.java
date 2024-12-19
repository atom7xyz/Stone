package xyz.sorridi.stone.common.utils.discord;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.logging.Logger;

/**
 * A simple logger that sends messages to a Discord channel.
 *
 * @author atom7xyz
 * @since 1.0
 */
@Getter
@Setter
public class StoneLogger
{
    private static final String DEFAULT_HOOK_NAME = "StoneLogger-API";
    private final Logger logger;

    private boolean online;
    private String hookURL, hookName;

    public StoneLogger(@NonNull Logger logger)
    {
        this.logger = logger;
        this.online = false;
        this.hookName = DEFAULT_HOOK_NAME;
    }

    /**
     * Creates a new StoneLogger with a default hook name.
     * @param logger The logger to use.
     * @param hookName The default hook name for this logger.
     */
    public StoneLogger(@NonNull Logger logger, @NonNull String hookName)
    {
        this(logger);
        this.hookName = hookName;
    }

    /**
     * Logs a message wit the default hookName.
     *
     * @param message The message to log.
     */
    public void message(@NonNull String message)
    {
        onlineSend(message);
    }

    /**
     * Logs a message with a hookName.
     *
     * @param hookName The hookName to log with.
     * @param message  The message to log.
     */
    public void message(@NonNull String hookName, @NonNull String message)
    {
        onlineSend(hookName, message);
    }

    /**
     * Logs a message as "[INFO]" with the given hook name.
     *
     * @param hookName The hookName to log with.
     * @param message  The message to log.
     */
    public void info(@NonNull String hookName, @NonNull String message)
    {
        onlineSend(hookName, "ℹ\uFE0F [INFO]: " + message);
        logger.info(message);
    }

    /**
     * Logs a message as "[INFO]" with the default hook name.
     *
     * @param message The message to log.
     */
    public void info(@NonNull String message)
    {
        info(hookName, message);
    }

    /**
     * Logs a message as "[WARN]" with the given hook name.
     *
     * @param hookName The hookName to log with.
     * @param message  The message to log.
     */
    public void warn(@NonNull String hookName, @NonNull String message)
    {
        onlineSend(hookName, "⚠\uFE0F [WARN]: " + message);
        logger.warning(message);
    }

    /**
     * Logs a message as "[WARN]" with the default hook name.
     *
     * @param message The message to log.
     */
    public void warn(@NonNull String message)
    {
        warn(hookName, message);
    }

    /**
     * Logs a message as "[ERROR]" with the given hook name.
     *
     * @param hookName The hookName to log with.
     * @param message  The message to log.
     */
    public void error(@NonNull String hookName, @NonNull String message)
    {
        onlineSend(hookName, "⛔ [ERROR]: " + message);
        logger.severe(message);
    }

    /**
     * Logs a message as "[ERROR]" with the default hook name.
     *
     * @param message The message to log.
     */
    public void error(@NonNull String message)
    {
        error(hookName, message);
    }

    /**
     * Logs a message as "[STACKTRACE]" with the given hook name.
     *
     * @param hookName  The hookName to log with.
     * @param exception The exception to log.
     */
    public void exception(@NonNull String hookName, @NonNull Exception exception)
    {
        onlineSend(hookName, "\uD83D\uDC1B [STACKTRACE]:\\n\\n " + formatStackTrace(exception));
        exception.printStackTrace();
    }

    /**
     * Logs a message as "[STACKTRACE]" with the default hook name.
     *
     * @param exception The exception to log.
     */
    public void exception(@NonNull Exception exception)
    {
        exception(hookName, exception);
    }

    /**
     * Sends a message to the discord channel with the default hook name.
     *
     * @param message The message to send.
     */
    private void onlineSend(@NonNull String message)
    {
        onlineSend(hookName, message);
    }

    /**
     * Sends a message to the discord channel.
     *
     * @param hookName The hook name to send with.
     * @param message  The message to send.
     */
    private void onlineSend(@NonNull String hookName, @NonNull String message)
    {
        if (!online || message.isEmpty())
        {
            return;
        }

        DiscordHook hook = new DiscordHook(hookURL);
        hook.setUsername(hookName);
        hook.setContent(message);
        hook.send();
    }

    /**
     * Format the stacktrace in a readable format.
     *
     * @param exception The exception to format.
     * @return The formatted stacktrace.
     */
    private String formatStackTrace(@NonNull Exception exception)
    {
        var builder = new StringBuilder();
        var stackTrace = exception.getStackTrace();

        builder.append(exception)
               .append("\\n");

        for (StackTraceElement element : stackTrace)
        {
            builder.append("\\tat ")
                   .append(element)
                   .append("\\n");
        }

        return builder.toString();
    }

}
