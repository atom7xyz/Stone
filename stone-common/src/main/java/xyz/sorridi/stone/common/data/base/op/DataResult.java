package xyz.sorridi.stone.common.data.base.op;

import org.jetbrains.annotations.NotNull;
import xyz.sorridi.stone.common.data.base.DataOrigin;

import java.sql.Connection;

/**
 * Represents an action that can be performed on a database.
 *
 * @param <T> The type of the result.
 * @author atom7xyz
 * @since 1.0
 */
public interface DataResult<T>
{
    T run(@NotNull Connection connection, @NotNull DataOrigin origin);
}