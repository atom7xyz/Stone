package xyz.sorridi.stone.spigot;

import lombok.SneakyThrows;
import me.lucko.helper.Commands;
import me.lucko.helper.internal.HelperImplementationPlugin;
import me.lucko.helper.plugin.ExtendedJavaPlugin;
import me.lucko.helper.plugin.ap.Plugin;
import me.lucko.helper.plugin.ap.PluginDependency;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.Configurator;
import org.bukkit.plugin.PluginLoadOrder;
import xyz.sorridi.stone.common.data.structures.SoftCleaner;
import xyz.sorridi.stone.spigot.annotations.serializer.SerializerProcessor;
import xyz.sorridi.stone.spigot.commands.StoneCommandImpl;

@Plugin(name = "stone-spigot",
        version = "1.0-SNAPSHOT",
        description = "Yet another spigot plugin library.",
        authors = "atom7xyz",
        website = "https://atom7.xyz",
        load = PluginLoadOrder.STARTUP,
        depends = @PluginDependency("helper"))

@HelperImplementationPlugin
public final class StoneSpigot extends ExtendedJavaPlugin
{
    static
    {
        System.setProperty("org.jooq.no-tips", "true");
        System.setProperty("org.jooq.no-logo", "true");

        var hikariLogger = (org.apache.logging.log4j.core.Logger) LogManager.getLogger("com.zaxxer.hikari");
        Configurator.setLevel(hikariLogger.getName(), Level.ERROR);
    }

    @Override
    @SneakyThrows
    public void enable()
    {
        SoftCleaner.setLogger(getLogger());

        new SerializerProcessor(this).process();

        Commands.create()
                .description("Stone command.")
                .handler(new StoneCommandImpl(this))
                .registerAndBind(this, "stone");
    }

    @Override
    public void disable()
    {
        // Plugin shutdown logic
    }

}
