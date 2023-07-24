package xyz.sorridi.stone.spigot.utils.services;

public class ServiceNotFoundException extends RuntimeException
{
    public ServiceNotFoundException(Class<?> service)
    {
        super("Service \"" + service.getSimpleName() + "\" not found.");
    }
}
