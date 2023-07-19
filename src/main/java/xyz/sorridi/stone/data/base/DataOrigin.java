package xyz.sorridi.stone.data.base;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.NonNull;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * Represents a connection to a database.
 *
 * @author Sorridi
 * @since 1.0
 */
public class DataOrigin
{
    private HikariDataSource dataSource;

    private String host, port, username, password, url, driver, database;
    private Map<String, String> properties;
    private boolean useDefaults;
    private int poolSize;

    public DataOrigin() {}

    public DataOrigin setHost(@NonNull String host)
    {
        this.host = host;
        return this;
    }

    public DataOrigin setPort(@NonNull String port)
    {
        this.port = port;
        return this;
    }

    public DataOrigin setUsername(@NonNull String username)
    {
        this.username = username;
        return this;
    }

    public DataOrigin setPassword(@Nullable String password)
    {
        this.password = password;
        return this;
    }

    public DataOrigin setUrl(@NonNull String url)
    {
        this.url = url;
        return this;
    }

    public DataOrigin setDriver(@NonNull String driver)
    {
        this.driver = driver;
        return this;
    }

    public DataOrigin setProperties(@NonNull Map<String, String> properties)
    {
        this.properties = properties;
        return this;
    }

    public DataOrigin setUseDefaults(boolean useDefaults)
    {
        this.useDefaults = useDefaults;
        return this;
    }

    public DataOrigin setPoolSize(int poolSize)
    {
        this.poolSize = poolSize;
        return this;
    }

    public DataOrigin setDatabase(@NonNull String database)
    {
        this.database = database;
        return this;
    }

    /**
     * Sets up the data source.
     *
     * @return The data origin.
     */
    public DataOrigin setup()
    {
        HikariConfig config = new HikariConfig();

        dataSource.setUsername(username);
        dataSource.setPassword(password);

        config.setDriverClassName(driver);
        config.setJdbcUrl("jdbc:" + url + "://" + host + ":" + port + "/" + database);

        if (poolSize > 0)
        {
            config.setMaximumPoolSize(poolSize);
        }
        else
        {
            config.setMaximumPoolSize(Runtime.getRuntime().availableProcessors() * 2);
        }

        if (useDefaults)
        {
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        }

        if (properties != null)
        {
            properties.forEach(config::addDataSourceProperty);
        }

        dataSource = new HikariDataSource(config);
        return this;
    }

    /**
     * Gets a connection from the data source.
     *
     * @return The connection.
     * @throws SQLException If an error occurs while getting the connection.
     */
    public Connection getConnection() throws SQLException
    {
        return dataSource.getConnection();
    }

    /**
     * Closes the specified connections.
     *
     * @param connections The connections to close.
     * @param <C>         The type of the connections.
     */
    public <C extends AutoCloseable> void close(C... connections)
    {
        for (C connection : connections)
        {
            try
            {
                connection.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * Shuts down the data source.
     */
    public void shutdown()
    {
        dataSource.close();
    }

}
