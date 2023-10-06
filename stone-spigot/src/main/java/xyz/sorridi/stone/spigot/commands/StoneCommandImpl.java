package xyz.sorridi.stone.spigot.commands;

import lombok.NonNull;
import me.lucko.helper.command.context.CommandContext;
import me.lucko.helper.command.functional.FunctionalCommandHandler;
import me.lucko.helper.plugin.ExtendedJavaPlugin;
import org.bukkit.command.CommandSender;
import xyz.sorridi.stone.common.commands.StoneCommand;
import xyz.sorridi.stone.common.data.structures.SoftCleaner;
import xyz.sorridi.stone.common.immutable.StonePerms;
import xyz.sorridi.stone.common.utils.Replace;
import xyz.sorridi.stone.spigot.utils.PluginVersion;
import xyz.sorridi.stone.spigot.utils.location.LocationEvaluate;

public class StoneCommandImpl extends StoneCommand implements FunctionalCommandHandler<CommandSender>, StonePerms
{

    public StoneCommandImpl(@NonNull ExtendedJavaPlugin javaPlugin)
    {
        super(new PluginVersion(javaPlugin));
    }

    @Override
    public void handle(@NonNull CommandContext<CommandSender> c)
    {
        c.reply(verMessage);

        if (!c.sender().hasPermission(STONE_STATS))
        {
            return;
        }

        updateStats(SoftCleaner.getNumInstances(),
                    Replace.getCacheSize(),
                    LocationEvaluate.getCacheSize());

        c.reply(statsMessage);
    }

}
