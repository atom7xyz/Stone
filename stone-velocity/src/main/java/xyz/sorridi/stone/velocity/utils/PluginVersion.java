package xyz.sorridi.stone.velocity.utils;

import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.plugin.PluginDescription;
import com.velocitypowered.api.plugin.meta.PluginDependency;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.sorridi.stone.common.utils.Replace;
import xyz.sorridi.stone.common.utils.VersionVerbose;

import java.util.List;

public final class PluginVersion extends VersionVerbose<PluginContainer, PluginDescription>
{
    public PluginVersion(PluginContainer plugin)
    {
        super(plugin);
    }

    public PluginVersion(PluginContainer plugin, int length)
    {
        super(plugin, length);
    }

    @Override
    public @NotNull PluginDescription getDescriptionFile()
    {
        return plugin.getDescription();
    }

    @Override
    public @NotNull String getName()
    {
        return getDescriptionFile().getName()
                                   .orElse(UNKNOWN);
    }

    @Override
    public @NotNull String getVersion()
    {
        return getDescriptionFile().getVersion()
                                   .orElse(UNKNOWN);
    }

    @Override
    public @Nullable String getSite()
    {
        return getDescriptionFile().getUrl()
                                   .orElse(null);
    }

    @Override
    public @Nullable String getAuthors()
    {
        return Replace.of(getDescriptionFile().getAuthors(), TO_REPLACE_ARRAY, "").toString();
    }

    @Override
    public @Nullable String getDescription()
    {
        return getDescriptionFile().getDescription()
                                   .orElse(null);
    }

    @Override
    public @Nullable List<String> getRawAuthors()
    {
        return getDescriptionFile().getAuthors();
    }

    @Override
    public @Nullable List<String> getDepends()
    {
        var dependencies = getDescriptionFile().getDependencies();

        if (dependencies.isEmpty())
        {
            return null;
        }

        return dependencies.stream()
                           .map(PluginDependency::getId)
                           .toList();
    }

    @Override
    public @Nullable List<String> getSoftDepends()
    {
        return null;
    }

}
