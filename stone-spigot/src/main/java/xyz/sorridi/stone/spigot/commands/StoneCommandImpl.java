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

public class StoneCommandImpl extends StoneCommand implements FunctionalCommandHandler<CommandSender>, StonePerms
{

    public StoneCommandImpl(@NonNull ExtendedJavaPlugin javaPlugin)
    {
        super(new PluginVersion(javaPlugin));
    }

    @Override
    public void handle(@NonNull CommandContext<CommandSender> c)
    {
        var source = c.sender();
        var args = c.args();

        switch (args.size())
        {
            case 0 ->
            {
                c.reply(verMessage);

                if (!source.hasPermission(STONE_STATS))
                    break;

                updateStats(SoftCleaner.getNumInstances(),
                            Replace.getCacheSize(),
                            -1);

                c.reply(statsMessage);
            }
            case 1 ->
            {
                if (!source.hasPermission(STONE_CLEAN))
                    break;

                if (args.get(0).equalsIgnoreCase("clean"))
                    c.reply(SoftCleaner.clean());
            }
        }
    }

}
