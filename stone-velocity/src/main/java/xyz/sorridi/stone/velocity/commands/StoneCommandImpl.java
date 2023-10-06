package xyz.sorridi.stone.velocity.commands;

import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.plugin.PluginContainer;
import lombok.NonNull;
import xyz.sorridi.stone.common.commands.StoneCommand;
import xyz.sorridi.stone.common.data.structures.SoftCleaner;
import xyz.sorridi.stone.common.immutable.StonePerms;
import xyz.sorridi.stone.common.utils.Replace;
import xyz.sorridi.stone.velocity.utils.PluginVersion;
import xyz.sorridi.stone.velocity.utils.Translate;

public class StoneCommandImpl extends StoneCommand implements SimpleCommand, StonePerms
{
    public StoneCommandImpl(@NonNull PluginContainer container)
    {
        super(new PluginVersion(container));
    }

    @Override
    public void execute(Invocation invocation)
    {
        var source = invocation.source();
        var args = invocation.arguments();

        switch (args.length)
        {
            case 0 ->
            {
                source.sendMessage(Translate.colors(verMessage));

                if (!source.hasPermission(STONE_STATS))
                {
                    break;
                }

                updateStats(SoftCleaner.getNumInstances(),
                            Replace.getCacheSize(),
                            -1);

                source.sendMessage(Translate.colors(statsMessage));
            }
            case 1 ->
            {
                if (!source.hasPermission(STONE_CLEAN))
                {
                    break;
                }

                if (args[0].equalsIgnoreCase("clean"))
                {
                    source.sendMessage(Translate.colors(SoftCleaner.clean()));
                }
            }
        }
    }

}
