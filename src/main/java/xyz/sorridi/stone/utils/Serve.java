package xyz.sorridi.stone.utils;

import lombok.val;
import me.lucko.helper.Services;

/**
 * Services utilities.
 * @author Sorridi
 * @since 1.0
 */
public class Serve
{

    /**
     * Gets a service that a plugin might have.
     * @param target The target service to get.
     * @return The service.
     * @param <G> The service type.
     * @throws NullPointerException If the service is not found.
     */
    public static <G> G of(Class<G> target) throws NullPointerException
    {
        val service = Services.get(target);

        if (service.isPresent())
        {
            return service.get();
        }

        throw new NullPointerException("Service " + target + " not found.");
    }

}
