package xyz.sorridi.stone.commands;

import me.lucko.helper.command.context.CommandContext;
import me.lucko.helper.command.functional.FunctionalCommandHandler;
import me.lucko.helper.plugin.ExtendedJavaPlugin;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import xyz.sorridi.stone.utils.Array;
import xyz.sorridi.stone.utils.Replace;
import xyz.sorridi.stone.utils.description.Versioning;

public class StoneCommand implements FunctionalCommandHandler<CommandSender>
{
    private static String[] VERSION_MESSAGE = Array.of(
            "&8&m-----------------------",
            "&8▎ &cv{ver} &7&o({hash})",
            "&8▎ &7autore: &c{authors}",
            "&8▎ &7sito: &c{site}",
            "&8&m-----------------------"
    );

    private static final String[] VERSION_TO_REPLACE = Array.of(
            "{ver}",
            "{hash}",
            "{authors}",
            "{site}"
    );

    public StoneCommand(ExtendedJavaPlugin plugin)
    {
        Versioning versioning = new Versioning(plugin);

        Replace.of(
                VERSION_MESSAGE,
                VERSION_TO_REPLACE,
                versioning.getVersion(),
                versioning.getHash(),
                versioning.getAuthors(),
                versioning.getSite()
        );
    }

    @Override
    public void handle(@NotNull CommandContext<CommandSender> c)
    {
        c.reply(VERSION_MESSAGE);
    }

}
