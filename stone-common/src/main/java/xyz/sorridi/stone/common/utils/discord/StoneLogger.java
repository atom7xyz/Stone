package xyz.sorridi.stone.common.utils.discord;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.logging.Logger;

@Getter
@Setter
public class StoneLogger
{
    private static final String DEFAULT_HOOK_NAME = "StoneLogger-API";
    private final Logger logger;

    private boolean online;
    private String hookURL, serverName;

    public StoneLogger(@NonNull Logger logger)
    {
        this.logger = logger;
        this.online = false;
        this.serverName = "Unknown Server";
    }

    public void custom(@NonNull String message)
    {
        onlineSend(message);
    }

    public void custom(@NonNull String username, @NonNull String message)
    {
        onlineSend(username, message);
    }

    public void info(@NonNull String username, @NonNull String message)
    {
        onlineSend(username, "ℹ\uFE0F [INFO]: " + message);
        logger.info(message);
    }

    public void info(@NonNull String message)
    {
        info(DEFAULT_HOOK_NAME, message);
    }

    public void warn(@NonNull String username, @NonNull String message)
    {
        onlineSend(username, "⚠\uFE0F [WARN]: " + message);
        logger.warning(message);
    }

    public void warn(@NonNull String message)
    {
        warn(DEFAULT_HOOK_NAME, message);
    }

    public void error(@NonNull String username, @NonNull String message)
    {
        onlineSend(username, "⛔ [ERROR]: " + message);
        logger.severe(message);
    }

    public void error(@NonNull String message)
    {
        error(DEFAULT_HOOK_NAME, message);
    }

    public void exception(@NonNull String username, @NonNull Exception exception)
    {
        onlineSend(username, "\uD83D\uDC1B [STACKTRACE]:\\n\\n " + formatStackTrace(exception));
        exception.printStackTrace();
    }

    public void exception(@NonNull Exception exception)
    {
        exception(DEFAULT_HOOK_NAME, exception);
    }

    private void onlineSend(@NonNull String message)
    {
        onlineSend("StoneLogger-API", message);
    }

    private void onlineSend(@NonNull String username, @NonNull String message)
    {
        if (!online)
        {
            return;
        }

        DiscordHook hook = new DiscordHook(hookURL);

        hook.setUsername(username);
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
