package xyz.sorridi.stone.common.data.base;

import java.util.Map;

/**
 * Configuration interface for the database.
 *
 * @author atom7xyz
 * @since 1.0
 */
public interface DataConfig
{
    default String getHost()
    {
        return "localhost";
    }

    default String getPort()
    {
        return "3306";
    }

    default String getUsername()
    {
        return "root";
    }

    default String getPassword()
    {
        return "";
    }

    default String getUrl()
    {
        return "mysql";
    }

    default String getDriver()
    {
        return "com.mysql.jdbc.Driver";
    }

    default String getDatabase()
    {
        return "stone";
    }

    default Map<String, String> getProperties()
    {
        return Map.of();
    }

    default boolean getUseDefaults()
    {
        return true;
    }

    default int getPoolSize()
    {
        return Runtime.getRuntime().availableProcessors() * 2;
    }
}
