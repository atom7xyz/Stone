package xyz.sorridi.stone.data.base;

import pl.mikigal.config.Config;

import java.util.Map;

/**
 * Configuration interface for the database.
 *
 * @author Sorridi
 * @since 1.0
 */
public interface DataConfig extends Config
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
        return 8;
    }
}
