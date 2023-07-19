package xyz.sorridi.stone.data.base;

import org.jetbrains.annotations.NotNull;

import java.sql.Connection;

/**
 * Represents an action that can be performed on a database.
 *
 * @param <T> The type of the result.
 * @author Sorridi
 * @since 1.0
 */
public interface DataAction<T>
{
    T run(@NotNull Connection connection, @NotNull DataOrigin origin);
}
