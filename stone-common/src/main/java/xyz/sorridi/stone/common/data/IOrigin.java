package xyz.sorridi.stone.common.data;

import lombok.NonNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

/**
 * Interface representing a data origin.
 * Provides methods for managing the connection details and lifecycle of the origin,
 * including acquiring and releasing resources, setting up, and handling errors during shutdown.
 *
 * @param <T> The type of the implementing class.
 *
 * @author atom7xyz
 * @since 1.0
 */
public interface IOrigin<T>
{
    AtomicBoolean ready = new AtomicBoolean(false);
    AtomicInteger ID = new AtomicInteger(0);
    Semaphore semaphore = new Semaphore(1);

    /**
     * Acquires the semaphore for exclusive access to the origin.
     *
     * @throws RuntimeException If the semaphore acquisition is interrupted.
     */
    default void acquire() throws RuntimeException
    {
        try
        {
            semaphore.acquire();
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException("Semaphore acquisition interrupted", e);
        }
    }

    /**
     * Releases the semaphore, allowing other threads to acquire it.
     */
    default void release()
    {
        semaphore.release();
    }

    /**
     * Sets the ready state of the origin.
     * If the origin is ready, the semaphore is released; otherwise, it's acquired.
     *
     * @param ready The ready state to set.
     */
    default void setReady(boolean ready)
    {
        IOrigin.ready.set(ready);

        if (ready)
        {
            release();
        }
        else
        {
            acquire();
        }
    }

    /**
     * Checks if the origin is ready.
     *
     * @return {@code true} if the origin is ready, {@code false} otherwise.
     */
    default boolean isReady()
    {
        return ready.get();
    }

    /**
     * Checks if the origin is not ready.
     *
     * @return {@code true} if the origin is not ready, {@code false} otherwise.
     */
    default boolean isNotReady()
    {
        return !isReady();
    }

    /**
     * Sets the host of the origin.
     *
     * @param host The host address.
     * @return The data origin instance (for method chaining).
     */
    T setHost(@NonNull String host);

    /**
     * Sets the port of the origin.
     *
     * @param port The port number.
     * @return The data origin instance (for method chaining).
     */
    T setPort(@NonNull String port);

    /**
     * Sets the username for the origin.
     *
     * @param username The username for authentication.
     * @return The data origin instance (for method chaining).
     */
    T setUsername(@Nullable String username);

    /**
     * Sets the password for the origin.
     *
     * @param password The password for authentication.
     * @return The data origin instance (for method chaining).
     */
    T setPassword(@Nullable String password);

    /**
     * Sets the pool size for the origin.
     *
     * @param poolSize The size of the connection pool.
     * @return The data origin instance (for method chaining).
     */
    T setPoolSize(int poolSize);

    /**
     * Sets the logger for the origin.
     *
     * @param logger The logger instance to be used for logging.
     * @return The data origin instance (for method chaining).
     */
    T setLogger(@NonNull Logger logger);

    /**
     * Performs the setup of the origin.
     * This method is expected to be implemented to initialize the origin's configuration.
     */
    void setup();

    /**
     * Performs the shutdown of the origin.
     * This method is expected to be implemented to handle the cleanup and resource release.
     */
    void shutdown();

    /**
     * Closes the given connections, suppressing any exceptions that occur during closure.
     *
     * @param connections The connections to close.
     * @param <C> The type of the connections.
     * @throws RuntimeException If one or more connections could not be closed.
     */
    default <C extends AutoCloseable> void close(C... connections)
    {
        if (connections == null)
        {
            return;
        }

        List<Exception> exceptions = new ArrayList<>();

        Arrays.stream(connections)
                .filter(Objects::nonNull)
                .forEach(connection ->
                {
                    try
                    {
                        connection.close();
                    }
                    catch (Exception e)
                    {
                        exceptions.add(e);
                    }
                });

        if (!exceptions.isEmpty())
        {
            RuntimeException runtimeException = new RuntimeException("Failed to close one or more connections");
            exceptions.forEach(runtimeException::addSuppressed);
            throw runtimeException;
        }
    }

}
