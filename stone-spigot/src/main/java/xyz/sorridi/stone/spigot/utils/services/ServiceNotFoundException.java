package xyz.sorridi.stone.spigot.utils.services;

/**
 * Exception thrown when a service is not found.
 *
 * @author atom7xyz
 * @since 1.0
 */
public class ServiceNotFoundException extends RuntimeException
{
    public ServiceNotFoundException(Class<?> service)
    {
        super("Service \"" + service.getSimpleName() + "\" not found.");
    }
}
