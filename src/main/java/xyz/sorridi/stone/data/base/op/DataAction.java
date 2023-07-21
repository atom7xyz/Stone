package xyz.sorridi.stone.data.base.op;

import org.jetbrains.annotations.NotNull;
import xyz.sorridi.stone.data.base.DataOrigin;

import java.sql.Connection;

/**
 * Represents an action that can be performed on a database.
 *
 * @author Sorridi
 * @since 1.0
 */
public interface DataAction
{
    void run(@NotNull Connection connection, @NotNull DataOrigin origin);
}