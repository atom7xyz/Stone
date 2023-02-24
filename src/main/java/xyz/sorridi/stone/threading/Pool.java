package xyz.sorridi.stone.threading;

import lombok.Getter;
import net.jodah.expiringmap.internal.NamedThreadFactory;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Getter
public class Pool implements Executor
{
    private final ExecutorService executor;
    private final int threads;
    private final String name;

    public Pool(String name, int threads)
    {
        this.threads    = threads;
        this.name       = name;
        executor        = Executors.newFixedThreadPool(threads, new NamedThreadFactory(name));
    }

    @Override
    public void execute(@NotNull Runnable command)
    {
        executor.execute(command);
    }

    public void shutdown()
    {
        executor.shutdown();
    }

}