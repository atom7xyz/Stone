package xyz.sorridi.stone.spigot.utils.services;

import me.lucko.helper.Services;

import java.util.function.Consumer;

/**
 * Utility class for working with services provided by plugins.
 * Facilitates retrieving and interacting with registered services.
 *
 * @author atom7xyz
 * @since 1.0
 */
public class Serve
{

    /**
     * Retrieves a service of the specified type.
     * If the service is not found, a {@link ServiceNotFoundException} is thrown.
     *
     * @param target The target service class to retrieve.
     * @param <G>    The type of the service.
     * @return The service instance.
     * @throws ServiceNotFoundException If the service is not found.
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
     * Executes the provided action with the service of the specified type if it exists.
     * If the service is not found, a {@link ServiceNotFoundException} is thrown.
     *
     * @param target   The target service class to retrieve.
     * @param consumer The action to perform with the retrieved service.
     * @param <G>      The type of the service.
     * @throws ServiceNotFoundException If the service is not found.
     */
    public static <G> void of(Class<G> target, Consumer<G> consumer) throws ServiceNotFoundException
    {
        var service = Services.get(target);

        if (service.isPresent())
        {
            consumer.accept(service.get());
        }
        else
        {
            throw new ServiceNotFoundException(target);
        }
    }

}
