package xyz.sorridi.stone.common.utils.discord;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.logging.Logger;

@Getter
@Setter
public class StoneLogger
{
    private final Logger logger;

    private boolean online;
    private String hookURL;

    public StoneLogger(@NonNull Logger logger)
    {
        this.logger = logger;
        this.online = false;
    }

    public StoneLogger(@NonNull Logger logger, boolean online)
    {
        this.logger = logger;
        this.online = online;
    }

    public void info(@NonNull String message)
    {
        onlineSend(message);
        logger.info(message);
    }

    public void warn(@NonNull String message)
    {
        onlineSend(message);
        logger.warning(message);
    }

    public void error(@NonNull String message)
    {
        onlineSend(message);
        logger.severe(message);
    }

    public void exception(@NonNull Exception exception)
    {
        String message = exception.getMessage();

        onlineSend(message == null ? exception.toString() : message);
        exception.printStackTrace();
    }

    private void onlineSend(@NonNull String message)
    {
        if (!online)
        {
            return;
        }

        DiscordHook hook = new DiscordHook(hookURL);

        hook.setUsername("Stone");
        hook.setContent(message);

        hook.send();
    }

}
