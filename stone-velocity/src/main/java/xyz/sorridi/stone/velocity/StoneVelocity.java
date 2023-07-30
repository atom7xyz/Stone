package xyz.sorridi.stone.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.Getter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configurator;
import xyz.sorridi.stone.velocity.utils.Players;
import xyz.sorridi.stone.velocity.utils.reply.Reply;

import java.util.logging.Logger;

@Plugin(id = "stone-velocity",
        name = "StoneVelocity",
        description = "Yet another velocity plugin library.",
        version = "1.0-SNAPSHOT",
        authors = {"Sorridi"})

@Getter
public class StoneVelocity
{
    static
    {
        System.setProperty("org.jooq.no-tips", "true");
        System.setProperty("org.jooq.no-logo", "true");

        var hikariLogger = (org.apache.logging.log4j.core.Logger) LogManager.getLogger("com.zaxxer.hikari");
        Configurator.setLevel(hikariLogger.getName(), Level.ERROR);
    }

    private final ProxyServer server;
    private final Logger logger;

    @Inject
    public StoneVelocity(ProxyServer server, Logger logger)
    {
        this.server = server;
        this.logger = logger;
    }

    @Subscribe
    public void on(ProxyInitializeEvent event)
    {
        Players.setServer(this);
        Reply.setServer(this);
    }

}