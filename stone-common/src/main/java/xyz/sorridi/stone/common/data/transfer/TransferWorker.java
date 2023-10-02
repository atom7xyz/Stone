package xyz.sorridi.stone.common.data.transfer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import xyz.sorridi.stone.common.data.transfer.op.TransferAction;
import xyz.sorridi.stone.common.threading.Pipeline;
import xyz.sorridi.stone.common.utils.discord.StoneLogger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A worker for transferring data.
 *
 * @author Sorridi
 * @since 1.0
 */
public class TransferWorker
{
    private static final AtomicInteger ID = new AtomicInteger(0);

    private final TransferOrigin origin;
    private final StoneLogger logger;
    private final Pipeline pipeline;

    private final BlockingQueue<TransferAction> queue;
    private TransferAction receiver;

    private boolean ready;

    public TransferWorker(TransferOrigin origin)
    {
        this.origin = origin;
        this.logger = origin.getLogger();
        this.pipeline = new Pipeline("transfer" + ID.getAndIncrement(), 1, 1);
        this.queue = new LinkedBlockingQueue<>();
    }

    /**
     * Starts the worker.
     */
    public void ready()
    {
        this.ready = true;

        CompletableFuture
                .runAsync(() ->
                          {
                              waitUntilReady();

                              while (ready)
                              {
                                  try (Connection conn = origin.getConnection(); Channel ch = conn.createChannel())
                                  {
                                      while (conn.isOpen() && ch.isOpen())
                                      {
                                          queue.take().with(conn, ch, origin);
                                      }
                                  }
                                  catch (Exception e)
                                  {
                                      logger.exception(e);
                                  }
                              }
                          }, pipeline.get(Pipeline.Types.WRITE));

        CompletableFuture
                .runAsync(() ->
                          {
                              waitUntilReady();

                              try
                              {
                                  Connection conn = origin.getConnection();
                                  Channel ch = conn.createChannel();

                                  receiver.with(conn, ch, origin);
                              }
                              catch (Exception e)
                              {
                                  logger.exception(e);
                              }
                          }, pipeline.get(Pipeline.Types.READ));
    }

    /**
     * Submits an action to the worker.
     * @param action The action to submit.
     */
    public boolean submit(TransferAction action)
    {
        return queue.offer(action);
    }

    /**
     * Sets the receiver of the worker.
     * @param action The receiver.
     */
    public void receiver(TransferAction action)
    {
        this.receiver = action;
    }

    /**
     * Shuts down the worker.
     */
    public void shutdown()
    {
        ready = false;
        pipeline.shutdown();
    }

    /**
     * Waits until the origin is ready.
     */
    private void waitUntilReady()
    {
        origin.acquire();
        origin.release();
    }

}
