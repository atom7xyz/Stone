package xyz.sorridi.stone.common.data.transfer;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.Getter;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.sorridi.stone.common.data.IOrigin;
import xyz.sorridi.stone.common.threading.Pool;
import xyz.sorridi.stone.common.utils.discord.StoneLogger;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

public class TransferOrigin implements IOrigin<TransferOrigin>
{
    private String host, port, username, password;
    private String vHost;
    private int poolSize;

    private Pool pool;

    @Getter
    private StoneLogger logger;

    private ConnectionFactory connectionFactory;

    public TransferOrigin()
    {
    }

    @Override
    public TransferOrigin setHost(@NotNull String host)
    {
        this.host = host;
        return this;
    }

    @Override
    public TransferOrigin setPort(@NotNull String port)
    {
        this.port = port;
        return this;
    }

    @Override
    public TransferOrigin setUsername(@Nullable String username)
    {
        this.username = username;
        return this;
    }

    @Override
    public TransferOrigin setPassword(String password)
    {
        this.password = password;
        return this;
    }

    @Override
    public TransferOrigin setPoolSize(int poolSize)
    {
        this.poolSize = poolSize;
        return this;
    }

    @Override
    public TransferOrigin setLogger(@NonNull Logger logger)
    {
        this.logger = new StoneLogger(logger);
        return this;
    }

    /**
     * Sets the vHost of the origin.
     *
     * @param vHost The vHost.
     * @return The data origin.
     */
    public TransferOrigin setVHost(@Nullable String vHost)
    {
        this.vHost = vHost;
        return this;
    }

    @Override
    public void setup()
    {
        setReady(false);
 
        connectionFactory = new ConnectionFactory();

        if (host != null)
        {
            connectionFactory.setHost(host);
        }

        if (port != null)
        {
            connectionFactory.setPort(Integer.parseInt(port));
        }

        if (username != null)
        {
            connectionFactory.setUsername(username);
        }

        if (password != null)
        {
            connectionFactory.setPassword(password);
        }

        if (vHost != null)
        {
            connectionFactory.setVirtualHost(vHost);
        }

        if (poolSize <= 0)
        {
            poolSize = Runtime.getRuntime().availableProcessors();
        }

        pool = new Pool("transfer_origin", poolSize);
        connectionFactory.setSharedExecutor(pool.getExecutor());

        pool.execute(() ->
                     {
                         while (!isReady())
                         {
                             try (Connection ignored = getConnection())
                             {
                                 logger.info(ID.get() + " - Connected to: " + host);
                                 setReady(true);
                             }
                             catch (Exception e)
                             {
                                 logger.error(ID.get() + " - Failed to connect to: " + host);

                                 try
                                 {
                                     Thread.sleep(1000);
                                 }
                                 catch (InterruptedException ex)
                                 {
                                     logger.exception(ex);
                                 }
                             }
                         }
                     });
    }

    @Override
    public void shutdown()
    {
        pool.shutdown();
        logger.info(ID.get() + " - Disconnected from: " + host);
    }

    /**
     * Gets a connection from the data origin.
     *
     * @return The connection.
     * @throws IOException      If an I/O error occurs.
     * @throws TimeoutException If the connection times out.
     */
    public Connection getConnection() throws IOException, TimeoutException
    {
        return connectionFactory.newConnection();
    }

}
