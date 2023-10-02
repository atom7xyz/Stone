package xyz.sorridi.stone.common.data.base;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.NonNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import xyz.sorridi.stone.common.data.IOrigin;
import xyz.sorridi.stone.common.threading.Pipeline;
import xyz.sorridi.stone.common.utils.discord.StoneLogger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Represents a connection to a database.
 *
 * @author Sorridi
 * @since 1.0
 */
public class DataOrigin implements IOrigin<DataOrigin>
{
    private HikariDataSource dataSource;
    private HikariConfig config;
    private StoneLogger logger;

    private String host, port, username, password;
    private int poolSize;

    private String url, driver, database;
    private Map<String, String> properties;
    private boolean useDefaults;

    public DataOrigin()
    {
    }

    @Override
    public DataOrigin setHost(@NonNull String host)
    {
        this.host = host;
        return this;
    }

    @Override
    public DataOrigin setPort(@NonNull String port)
    {
        this.port = port;
        return this;
    }

    @Override
    public DataOrigin setUsername(@Nullable String username)
    {
        this.username = username;
        return this;
    }

    @Override
    public DataOrigin setPassword(@Nullable String password)
    {
        this.password = password;
        return this;
    }

    @Override
    public DataOrigin setPoolSize(int poolSize)
    {
        this.poolSize = poolSize;
        return this;
    }

    @Override
    public DataOrigin setLogger(@NonNull Logger logger)
    {
        this.logger = new StoneLogger(logger);
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

    @Override
    public void setup()
    {
        setReady(false);

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
                           worker.shutdown();

                           setDatabase(database);
                           config.setJdbcUrl("jdbc:" + url + "://" + host + ":" + port + "/" + database);

                           dataSource = new HikariDataSource(config);
                           setReady(true);
                       });
    }

    @Override
    public void shutdown()
    {
        dataSource.close();
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
     * Gets whether the data source is ready.
     *
     * @return Whether the data source is ready.
     */
    public boolean isReady()
    {
        return ready.get();
    }

    public void waitUntilReady()
    {
        acquire();
        release();
    }

}
