package xyz.sorridi.stone.spigot.commands;

import lombok.NonNull;
import me.lucko.helper.command.context.CommandContext;
import me.lucko.helper.command.functional.FunctionalCommandHandler;
import me.lucko.helper.plugin.ExtendedJavaPlugin;
import org.bukkit.command.CommandSender;
import xyz.sorridi.stone.common.data.structures.SoftCleaner;
import xyz.sorridi.stone.common.utils.Replace;
import xyz.sorridi.stone.common.utils.data.Array;
import xyz.sorridi.stone.spigot.utils.PluginVersion;
import xyz.sorridi.stone.spigot.utils.location.LocationEvaluate;

public class StoneCommand implements FunctionalCommandHandler<CommandSender>
{
    private static final String[] VERSION_MESSAGE = Array.of("&8&m-----------------------",
                                                             "&8▎ &7ver: &cv{ver} &7&o({hash})",
                                                             "&8▎ &7autore: &c{authors}",
                                                             "&8▎ &7sito: &c{site}",
                                                             "&8&m-----------------------");

    private static final String[] STATS_MESSAGE = Array.of("&8&m-----------------------",
                                                           "&8▎ &c&lSTATISTICHE",
                                                           "&8▎ &cSoftMaps qty: &7{softmaps}",
                                                           "&8▎ &cReplace cache size: &7{replace}",
                                                           "&8▎ &cLocation cache size: &7{location}",
                                                           "&8&m-----------------------");

    private static final String[] VERSION_TO_REPLACE = Array.of("{ver}",
                                                                "{hash}",
                                                                "{authors}",
                                                                "{site}");

    private static final String[] STATS_TO_REPLACE = Array.of("{softmaps}",
                                                              "{replace}",
                                                              "{location}");

    private final String[] verMessage;

    public StoneCommand(@NonNull ExtendedJavaPlugin plugin)
    {
        var verbose = new PluginVersion(plugin);

        verMessage = Replace.of(VERSION_MESSAGE,
                                VERSION_TO_REPLACE,
                                verbose.getVersion(),
                                verbose.getHash(),
                                verbose.getAuthors(),
                                verbose.getSite());
    }

    @Override
    public void handle(@NonNull CommandContext<CommandSender> c)
    {
        c.reply(verMessage);

        var statsMessage = Replace.of(STATS_MESSAGE,
                                      STATS_TO_REPLACE,
                                      SoftCleaner.getNumInstances(),
                                      Replace.getCacheSize(),
                                      LocationEvaluate.IS_NEAR_CACHE.size());
        c.reply(statsMessage);
    }

}
