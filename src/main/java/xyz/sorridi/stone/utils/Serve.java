package xyz.sorridi.stone.utils;

import lombok.val;
import me.lucko.helper.Services;
import me.lucko.helper.plugin.ExtendedJavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * Services utilities.
 * @author Sorridi
 * @since 1.0
 */
public class Serve
{

    /**
     * Gets a service that a plugin might have.
     * @param plugin The class of the plugin.
     * @return The service.
     * @param <K> The type of the service.
     * @throws NullPointerException If the service is not found.
     */
    public static <G, K extends ExtendedJavaPlugin> G of(@NotNull Class<K> plugin, Class<G> target) throws NullPointerException
    {
        val service = Services.get(target);

        if (service.isPresent())
        {
            return service.get();
        }

        throw new NullPointerException("Service of " + plugin + " not found.");
    }

}
