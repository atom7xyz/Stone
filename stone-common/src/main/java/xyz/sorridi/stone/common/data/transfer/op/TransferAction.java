package xyz.sorridi.stone.common.data.transfer.op;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.jetbrains.annotations.NotNull;
import xyz.sorridi.stone.common.data.transfer.TransferOrigin;

public interface TransferAction
{
    void with(@NotNull Connection connection, @NotNull Channel channel, @NotNull TransferOrigin origin) throws
                                                                                                        Exception;
}
