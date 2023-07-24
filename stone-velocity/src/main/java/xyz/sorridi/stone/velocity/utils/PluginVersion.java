package xyz.sorridi.stone.velocity.utils;

import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.plugin.PluginDescription;
import com.velocitypowered.api.plugin.meta.PluginDependency;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.sorridi.stone.common.utils.Replace;
import xyz.sorridi.stone.common.utils.VersionVerbose;
import xyz.sorridi.stone.common.utils.data.Array;

import java.util.List;

public final class PluginVersion extends VersionVerbose<PluginContainer, PluginDescription>
{

    public PluginVersion(PluginContainer plugin)
    {
        super(plugin);
    }

    @Override
    public @NotNull PluginDescription getDescriptionFile()
    {
        return plugin.getDescription();
    }

    @Override
    public @NotNull String getName()
    {
        return String.valueOf(getDescriptionFile().getName());
    }

    @Override
    public @NotNull String getVersion()
    {
        return String.valueOf(getDescriptionFile().getVersion());
    }

    @Override
    public @Nullable String getSite()
    {
        return String.valueOf(getDescriptionFile().getUrl());
    }

    @Override
    public @Nullable String getAuthors()
    {
        return Replace.of(getDescriptionFile().getAuthors(), Array.of("[", "]"), "").toString();
    }

    @Override
    public @Nullable String getDescription()
    {
        return null;
    }

    @Override
    public @Nullable List<String> getRawAuthors()
    {
        return getDescriptionFile().getAuthors();
    }

    @Override
    public @Nullable List<String> getDepends()
    {
        return getDescriptionFile().getDependencies()
                                   .stream()
                                   .map(PluginDependency::getId)
                                   .toList();
    }

    @Override
    public @Nullable List<String> getSoftDepends()
    {
        return null;
    }

}
