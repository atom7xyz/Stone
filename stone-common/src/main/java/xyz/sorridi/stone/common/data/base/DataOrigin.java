package xyz.sorridi.stone.common.data.base;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.NonNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import xyz.sorridi.stone.common.threading.Pipeline;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Represents a connection to a database.
 *
 * @author Sorridi
 * @since 1.0
 */
public class DataOrigin
{
    private HikariDataSource dataSource;
    private HikariConfig config;

    private final AtomicBoolean ready = new AtomicBoolean(false);

    private String host, port, username, password, url, driver, database;
    private Map<String, String> properties;
    private boolean useDefaults;
    private int poolSize;

    public DataOrigin() {}

    /**
     * Sets the host of the origin.
     *
     * @param host The host.
     * @return The data origin.
     */
    public DataOrigin setHost(@NonNull String host)
    {
        this.host = host;
        return this;
    }

    /**
     * Sets the port of the origin.
     *
     * @param port The port.
     * @return The data origin.
     */
    public DataOrigin setPort(@NonNull String port)
    {
        this.port = port;
        return this;
    }

    /**
     * Sets the username of the origin.
     *
     * @param username The username.
     * @return The data origin.
     */
    public DataOrigin setUsername(@NonNull String username)
    {
        this.username = username;
        return this;
    }

    /**
     * Sets the password of the origin.
     *
     * @param password The password.
     * @return The data origin.
     */
    public DataOrigin setPassword(@Nullable String password)
    {
        this.password = password;
        return this;
    }

    /**
     * Sets the url of the origin.
     *
     * @param url The url.
     * @return The data origin.
     */
    public DataOrigin setUrl(@NonNull String url)
    {
        this.url = url;
        return this;
    }

    /**
     * Sets the driver of the origin.
     *
     * @param driver The driver.
     * @return The data origin.
     */
    public DataOrigin setDriver(@NonNull String driver)
    {
        this.driver = driver;
        return this;
    }

    /**
     * Sets the properties of the origin.
     *
     * @param properties The properties.
     * @return The data origin.
     */
    public DataOrigin setProperties(@NonNull Map<String, String> properties)
    {
        this.properties = properties;
        return this;
    }

    /**
     * Sets whether to use defaults.
     *
     * @param useDefaults Whether to use defaults.
     * @return The data origin.
     */
    public DataOrigin setUseDefaults(boolean useDefaults)
    {
        this.useDefaults = useDefaults;
        return this;
    }

    /**
     * Sets the pool size.
     *
     * @param poolSize The pool size.
     * @return The data origin.
     */
    public DataOrigin setPoolSize(int poolSize)
    {
        this.poolSize = poolSize;
        return this;
    }

    /**
     * Sets the database and updates the data source.
     *
     * @param database The database.
     * @return The data origin.
     */
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
        config = new HikariConfig();

        config.setUsername(username);
        config.setPassword(password);

        config.setDriverClassName(driver);
        config.setJdbcUrl("jdbc:" + url + "://" + host + ":" + port + "/");

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

        DataWorker worker = new DataWorker(this, true);
        worker.submit((connection, origin) ->
                      {
                          DSLContext context = DSL.using(connection);

                          context.createDatabaseIfNotExists(database)
                                 .execute();
                      },
                      Pipeline.Types.WRITE)
              .thenRun(() ->
                       {
                           close(dataSource);

                           setDatabase(database);
                           config.setJdbcUrl("jdbc:" + url + "://" + host + ":" + port + "/" + database);

                           dataSource = new HikariDataSource(config);
                           ready.set(true);

                           worker.shutdown();
                       });

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

    /**
     * Gets whether the data source is ready.
     *
     * @return Whether the data source is ready.
     */
    public boolean isReady()
    {
        return ready.get();
    }

}
