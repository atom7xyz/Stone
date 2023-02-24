package xyz.sorridi.stone.threading;

import org.jetbrains.annotations.NotNull;

public class Pipeline
{
    private final Pool readPool;
    private final Pool writePool;

    public Pipeline(String name, int readThreads, int writeThreads)
    {
        readPool    = new Pool("read-" + name, readThreads);
        writePool   = new Pool("write-" + name, writeThreads);
    }

    public void execute(@NotNull PipelineTypes type, Runnable runnable)
    {
        switch (type)
        {
            case READ   -> readPool.execute(runnable);
            case WRITE  -> writePool.execute(runnable);
        }
    }

    public void shutdown()
    {
        readPool.shutdown();
        writePool.shutdown();
    }

}