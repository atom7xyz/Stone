package xyz.sorridi.stone.utils.bukkit.services;

public class ServiceNotFoundException extends RuntimeException
{
    public ServiceNotFoundException(Class<?> service)
    {
        super("Service \"" + service.getSimpleName() + "\" not found.");
    }
}
