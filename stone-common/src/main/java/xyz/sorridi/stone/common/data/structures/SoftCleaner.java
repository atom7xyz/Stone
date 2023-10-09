package xyz.sorridi.stone.common.data.structures;

import java.lang.ref.SoftReference;
import java.time.Duration;
import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

/**
 * An automated scheduled cleaner for {@link SoftMap}s.
 *
 * @author Sorridi
 * @since 1.0
 */
public class SoftCleaner
{
    private static final ConcurrentLinkedQueue<SoftReference<SoftMap<?, ?>>> INSTANCES;
    private static final Stack<SoftReference<SoftMap<?, ?>>> TO_REMOVE;

    private static ScheduledExecutorService TASK;

    private static boolean logging;
    private static Logger logger;

    static
    {
        TO_REMOVE = new Stack<>();
        INSTANCES = new ConcurrentLinkedQueue<>();

        schedule(Duration.ofMinutes(10));
    }

    /**
     * Schedules the cleaner. (It is currently not possible to override the default duration)
     *
     * @param duration The duration between each clean.
     */
    public static void schedule(Duration duration)
    {
        if (TASK == null)
        {
            TASK = Executors.newScheduledThreadPool(1);
        }

        TASK.scheduleAtFixedRate(SoftCleaner::clean, duration.toSeconds(), duration.toSeconds(), TimeUnit.SECONDS);
    }

    /**
     * Adds a {@link SoftMap} to the cleaner.
     *
     * @param map The {@link SoftMap} to add.
     */
    public static void add(SoftMap<?, ?> map)
    {
        INSTANCES.add(new SoftReference<>(map));
    }

    /**
     * Removes a {@link SoftMap} from the cleaner.
     *
     * @param map The {@link SoftMap} to remove.
     */
    public static void remove(SoftReference<SoftMap<?, ?>> map)
    {
        INSTANCES.remove(map);
    }

    /**
     * Cleans all the {@link SoftMap} instances.
     */
    public static String clean()
    {
        AtomicInteger count = new AtomicInteger();

        INSTANCES.forEach(s ->
                          {
                              var instance = s.get();

                              if (instance == null)
                              {
                                  TO_REMOVE.add(s);
                              }
                              else
                              {
                                  int res = instance.clean();

                                  if (instance.size() == 0)
                                  {
                                      TO_REMOVE.add(s);
                                  }

                                  count.addAndGet(res);
                              }
                          });

        var message = "Expired / Removed / Left: " + count.get() + " / " + TO_REMOVE.size() + " / " + INSTANCES.size();

        TO_REMOVE.forEach(SoftCleaner::remove);
        TO_REMOVE.clear();

        if (logging)
        {
            info(message);
        }

        return message;
    }

    /**
     * Sets the logging state.
     *
     * @param logging The logging state.
     */
    public static void setLogging(boolean logging)
    {
        SoftCleaner.logging = logging;
    }

    /**
     * Sets the logger and activates logging.
     *
     * @param logger The logger.
     */
    public static void setLogger(Logger logger)
    {
        SoftCleaner.logging = true;
        SoftCleaner.logger = logger;
        info("Logger set!");
    }

    /**
     * Logs an info message.
     *
     * @param message The message to log.
     */
    private static void info(String message)
    {
        logger.info("[SoftCleaner] " + message);
    }

    /**
     * Gets the number of {@link SoftMap} instances.
     *
     * @return The number of {@link SoftMap} instances.
     */
    public static int getNumInstances()
    {
        return INSTANCES.size();
    }

}
