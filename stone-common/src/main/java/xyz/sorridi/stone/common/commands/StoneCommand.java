package xyz.sorridi.stone.common.commands;

import xyz.sorridi.stone.common.utils.Replace;
import xyz.sorridi.stone.common.utils.VersionVerbose;
import xyz.sorridi.stone.common.utils.data.Array;

public class StoneCommand
{
    protected static final String[] VERSION_MESSAGE = Array.of("&8&m-----------------------",
                                                             "&8▎ &7ver: &cv{ver} &7&o({hash})",
                                                             "&8▎ &7autore: &c{authors}",
                                                             "&8▎ &7sito: &c{site}",
                                                             "&8&m-----------------------");

    protected static final String[] STATS_MESSAGE = Array.of("&8&m-----------------------",
                                                           "&8▎ &c&lSTATISTICHE",
                                                           "&8▎ &cSoftMaps tot: &7{softmaps}",
                                                           "&8▎ &cReplace cache size: &7{replace}",
                                                           "&8▎ &cLocation cache size: &7{location}",
                                                           "&8&m-----------------------");

    protected static final String[] VERSION_TO_REPLACE = Array.of("{ver}",
                                                                "{hash}",
                                                                "{authors}",
                                                                "{site}");

    protected static final String[] STATS_TO_REPLACE = Array.of("{softmaps}",
                                                              "{replace}",
                                                              "{location}");

    protected final String[] verMessage;
    protected String[] statsMessage;

    public <V extends VersionVerbose<?, ?>> StoneCommand(V version)
    {
        verMessage = Replace.of(VERSION_MESSAGE,
                                VERSION_TO_REPLACE,
                                version.getVersion(),
                                version.getHash(),
                                version.getAuthors(),
                                version.getSite());
    }

    protected void updateStats(int softMaps, int replaces, int locations)
    {
        statsMessage = Replace.of(STATS_MESSAGE,
                                  STATS_TO_REPLACE,
                                  softMaps,
                                  replaces,
                                  checkUnused(locations));
    }

    private Object checkUnused(int i)
    {
        return i == -1 ? "unused" : i;
    }

}
