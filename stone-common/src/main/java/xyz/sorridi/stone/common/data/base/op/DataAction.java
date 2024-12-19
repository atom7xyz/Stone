package xyz.sorridi.stone.common.data.base.op;

import org.jetbrains.annotations.NotNull;
import xyz.sorridi.stone.common.data.base.DataOrigin;

import java.sql.Connection;

/**
 * Represents an action that can be performed on a database.
 *
 * @author atom7xyz
 * @since 1.0
 */
public interface DataAction
{
    void run(@NotNull Connection connection, @NotNull DataOrigin origin);
}