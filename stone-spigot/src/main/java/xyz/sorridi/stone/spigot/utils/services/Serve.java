package xyz.sorridi.stone.spigot.utils.services;

import me.lucko.helper.Services;

import java.util.function.Consumer;

/**
 * Services utilities.
 *
 * @author Sorridi
 * @since 1.0
 */
public class Serve
{

    /**
     * Gets a service that a plugin might have.
     *
     * @param target The target service to get.
     * @param <G>    The service type.
     * @return The service.
     * @throws NullPointerException If the service is not found.
     */
    public static <G> G of(Class<G> target) throws ServiceNotFoundException
    {
        var service = Services.get(target);

        if (service.isPresent())
        {
            return service.get();
        }

        throw new ServiceNotFoundException(target);
    }

    /**
     * Gets a service that a plugin might have.
     *
     * @param target   The target service to get.
     * @param consumer The action to execute if the service is found.
     * @param <G>      The service type.
     */
    public static <G> void of(Class<G> target, Consumer<G> consumer) throws ServiceNotFoundException
    {
        var service = Services.get(target);

        service.ifPresent(consumer);

        throw new ServiceNotFoundException(target);
    }

}
