package xyz.sorridi.stone.spigot.utils;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.sorridi.stone.common.utils.Replace;
import xyz.sorridi.stone.common.utils.VersionVerbose;
import xyz.sorridi.stone.common.utils.data.Array;

import java.util.List;

public final class PluginVersion extends VersionVerbose<Plugin, PluginDescriptionFile>
{

    public PluginVersion(Plugin plugin)
    {
        super(plugin);
    }

    @Override
    public @NotNull PluginDescriptionFile getDescriptionFile()
    {
        return plugin.getDescription();
    }

    @Override
    public @NotNull String getName()
    {
        return plugin.getName();
    }

    @Override
    public @NotNull String getVersion()
    {
        return getDescriptionFile().getVersion();
    }

    @Override
    public @Nullable String getSite()
    {
        return getDescriptionFile().getWebsite();
    }

    @Override
    public @Nullable String getAuthors()
    {
        return Replace.of(getDescriptionFile().getAuthors(), Array.of("[", "]"), "").toString();
    }

    @Override
    public @Nullable String getDescription()
    {
        return getDescriptionFile().getDescription();
    }

    @Override
    public @Nullable List<String> getRawAuthors()
    {
        return getDescriptionFile().getAuthors();
    }

    @Override
    public @Nullable List<String> getDepends()
    {
        return getDescriptionFile().getDepend();
    }

    @Override
    public @Nullable List<String> getSoftDepends()
    {
        return getDescriptionFile().getSoftDepend();
    }

}
