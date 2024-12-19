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
 * Handles the processing and transferring of data using a queue and concurrent tasks.
 *
 * @author atom7xyz
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

    private volatile boolean ready;

    public TransferWorker(TransferOrigin origin)
    {
        this.origin = origin;
        this.logger = origin.getLogger();
        this.pipeline = new Pipeline("transfer" + ID.getAndIncrement(), 1, 1);
        this.queue = new LinkedBlockingQueue<>();
        this.ready = false;
    }

    /**
     * Starts the worker by initiating two asynchronous tasks:
     * - One for continuously processing actions from the queue.
     * - Another for processing the receiver action.
     */
    public void ready()
    {
        this.ready = true;

        // Start the writer task
        CompletableFuture.runAsync(() -> {
            waitUntilReady();

            while (ready)
            {
                try (Connection conn = origin.getConnection(); Channel ch = conn.createChannel())
                {
                    // Process actions from the queue as long as the connection and channel are open
                    TransferAction action = queue.take(); // Blocks until an action is available
                    action.with(conn, ch, origin);
                }
                catch (Exception e)
                {
                    logger.exception("Error processing action in writer task", e);
                }
            }
        }, pipeline.get(Pipeline.Types.WRITE));

        // Start the reader task
        CompletableFuture.runAsync(() -> {
            waitUntilReady();

            try (Connection conn = origin.getConnection(); Channel ch = conn.createChannel())
            {
                // Process the receiver action once the worker is ready
                if (receiver != null)
                {
                    receiver.with(conn, ch, origin);
                }
            }
            catch (Exception e)
            {
                logger.exception("Error processing receiver action in reader task", e);
            }
        }, pipeline.get(Pipeline.Types.READ));
    }

    /**
     * Submits an action to the worker.
     * The action is placed in the queue for processing by the worker.
     *
     * @param action The action to submit.
     * @return {@code true} if the action was successfully added to the queue; {@code false} otherwise.
     */
    public boolean submit(TransferAction action)
    {
        return queue.offer(action);  // Returns false if the action couldn't be added (queue full)
    }

    /**
     * Sets the receiver action to be processed.
     * The receiver action will be executed once the worker is ready.
     *
     * @param action The receiver action.
     */
    public void receiver(TransferAction action)
    {
        this.receiver = action;
    }

    /**
     * Shuts down the worker.
     * This method stops the worker from processing further actions and shuts down the pipeline.
     */
    public void shutdown()
    {
        ready = false;
        pipeline.shutdown();
    }

    /**
     * Waits until the origin is ready for processing.
     * It acquires and then immediately releases the semaphore to synchronize the readiness.
     */
    private void waitUntilReady()
    {
        origin.acquire(); // Block until the origin is ready
        origin.release(); // Immediately release it
    }
}
