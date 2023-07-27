package xyz.sorridi.stone.spigot;

import lombok.SneakyThrows;
import me.lucko.helper.Commands;
import me.lucko.helper.internal.HelperImplementationPlugin;
import me.lucko.helper.plugin.ExtendedJavaPlugin;
import me.lucko.helper.plugin.ap.Plugin;
import me.lucko.helper.plugin.ap.PluginDependency;
import org.bukkit.plugin.PluginLoadOrder;
import xyz.sorridi.stone.spigot.annotations.serializer.SerializerProcessor;
import xyz.sorridi.stone.spigot.commands.StoneCommand;

@Plugin(name = "Stone",
        version = "1.0-SNAPSHOT",
        description = "Yet another spigot plugin library.",
        authors = "Sorridi",
        website = "https://sorridi.xyz",
        load = PluginLoadOrder.STARTUP,
        depends = @PluginDependency("helper"))

@HelperImplementationPlugin
public final class StoneSpigot extends ExtendedJavaPlugin
{
    static
    {
        System.setProperty("org.jooq.no-tips", "true");
    }

    @Override
    @SneakyThrows
    public void enable()
    {
        new SerializerProcessor(this).process();

        Commands.create()
                .description("Stone command.")
                .handler(new StoneCommand(this))
                .registerAndBind(this, "stone");
    }

    @Override
    public void disable()
    {
        // Plugin shutdown logic
    }

}
