package xyz.sorridi.stone.common.data;

import lombok.NonNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public interface IOrigin<T>
{
    AtomicBoolean ready = new AtomicBoolean(false);
    AtomicInteger ID = new AtomicInteger(0);
    Semaphore semaphore = new Semaphore(1);

    /**
     * Acquires the semaphore.
     */
    default void acquire() throws RuntimeException
    {
        try
        {
            semaphore.acquire();
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Releases the semaphore.
     */
    default void release()
    {
        semaphore.release();
    }

    /**
     * Sets the ready state of the origin.
     *
     * @param ready The ready state.
     */
    default void setReady(boolean ready)
    {
        this.ready.set(ready);

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
     * Gets the ready state of the origin.
     *
     * @return The ready state.
     */
    default boolean isReady()
    {
        return ready.get();
    }

    /**
     * Sets the host of the origin.
     *
     * @param host The host.
     * @return The data origin.
     */
    T setHost(@NonNull String host);

    /**
     * Sets the port of the origin.
     *
     * @param port The port.
     * @return The data origin.
     */
    T setPort(@NonNull String port);

    /**
     * Sets the username of the origin.
     *
     * @param username The username.
     * @return The data origin.
     */
    T setUsername(@Nullable String username);

    /**
     * Sets the username of the origin.
     *
     * @param password The username.
     * @return The data origin.
     */
    T setPassword(@Nullable String password);

    /**
     * Sets the password of the origin.
     *
     * @param poolSize The pool size.
     * @return The data origin.
     */
    T setPoolSize(int poolSize);

    /**
     * Sets the logger of the origin.
     *
     * @param logger The logger.
     * @return The data origin.
     */
    T setLogger(@NonNull Logger logger);

    /**
     * Performs the setup of the origin.
     */
    void setup();

    /**
     * Performs the shutdown of the origin.
     */
    void shutdown();

    /**
     * Closes the given connections.
     *
     * @param connections The connections.
     * @param <C>         The type of the connections.
     */
    default <C extends AutoCloseable> void close(C... connections)
    {
        if (connections == null)
        {
            return;
        }

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
                               throw new RuntimeException(e);
                           }
                       });
    }
}
