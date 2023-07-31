package xyz.sorridi.stone.velocity.utils.reply;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.NonNull;
import net.kyori.adventure.text.TextComponent;
import xyz.sorridi.stone.common.utils.IReply;
import xyz.sorridi.stone.common.utils.data.Array;
import xyz.sorridi.stone.velocity.StoneVelocity;
import xyz.sorridi.stone.velocity.utils.Players;

import java.util.Collection;
import java.util.HashSet;

/**
 * Messaging utilities.
 *
 * @author Sorridi
 * @since 1.0
 */
public class ReplyImpl implements IReply<Player, TextComponent>
{
    private final ProxyServer server;

    public ReplyImpl(StoneVelocity plugin)
    {
        this.server = plugin.getServer();
    }

    @Override
    public boolean to(Player player, TextComponent... messages)
    {
        if (player == null || Array.isEmpty(messages))
        {
            return false;
        }

        for (TextComponent message : messages)
        {
            if (!message.content().isEmpty())
            {
                player.sendMessage(message);
            }
        }

        return true;
    }

    @Override
    public <C extends Collection<TextComponent>> boolean to(Player player, C collection)
    {
        if (player == null || Array.isEmpty(collection))
        {
            return false;
        }

        collection.stream()
                  .filter(message -> !message.content().isEmpty())
                  .forEach(message -> to(player, message));

        return true;
    }

    @Override
    public void toAll(TextComponent... messages)
    {
        to(server.getAllPlayers(), messages);
    }

    @Override
    public <P extends Collection<TextComponent>> void toAll(P collection)
    {
        to(server.getAllPlayers(), collection);
    }

    @Override
    public void toAllExcept(@NonNull Player exclude, TextComponent... messages)
    {
        if (Array.isEmpty(messages))
        {
            return;
        }

        Players.streamAll()
               .filter(p -> !p.equals(exclude))
               .forEach(p -> to(p, messages));
    }

    @Override
    public <C extends Collection<TextComponent>> void toAllExcept(@NonNull Player toExclude, C collection)
    {
        if (Array.isEmpty(collection))
        {
            return;
        }

        Players.streamAll()
               .filter(p -> !p.equals(toExclude))
               .forEach(p -> to(p, collection));
    }

    @Override
    public <P extends Collection<Player>> void toAllExcept(@NonNull P toExclude, TextComponent... messages)
    {
        if (Array.isEmpty(messages))
        {
            return;
        }

        HashSet<Player> excluded = new HashSet<>(toExclude);

        Players.streamAll()
               .filter(p -> !excluded.contains(p))
               .forEach(p -> to(p, messages));
    }

    @Override
    public <P extends Collection<Player>, C extends Collection<TextComponent>> void toAllExceptMulti(@NonNull P toExclude,
                                                                                                     C collection)
    {
        if (Array.isEmpty(collection))
        {
            return;
        }

        HashSet<Player> excluded = new HashSet<>(toExclude);

        Players.streamAll()
               .filter(p -> !excluded.contains(p))
               .forEach(p -> to(p, collection));
    }

    @Override
    public <P extends Collection<Player>> void toExcept(P players, @NonNull P exclude, TextComponent... messages)
    {
        if (Array.isEmpty(messages))
        {
            return;
        }

        players.stream()
               .filter(p -> !exclude.contains(p))
               .forEach(p -> to(p, messages));
    }

    @Override
    public <P extends Collection<Player>, C extends Collection<TextComponent>> void toExcept(P players,
                                                                                             @NonNull P exclude,
                                                                                             C collection)
    {
        if (Array.isEmpty(collection))
        {
            return;
        }

        players.stream()
               .filter(p -> !exclude.contains(p))
               .forEach(p -> to(p, collection));
    }

    @Override
    public <P extends Collection<Player>> void toExcept(P players, @NonNull Player exclude, TextComponent... messages)
    {
        if (Array.isEmpty(messages))
        {
            return;
        }

        players.stream()
               .filter(p -> !p.equals(exclude))
               .forEach(p -> to(p, messages));
    }

    @Override
    public <P extends Collection<Player>, C extends Collection<TextComponent>> void toExcept(P players,
                                                                                             @NonNull Player exclude,
                                                                                             C collection)
    {
        if (Array.isEmpty(collection))
        {
            return;
        }

        players.stream()
               .filter(p -> !p.equals(exclude))
               .forEach(p -> to(p, collection));
    }

    @Override
    public void toAllWithPerm(@NonNull String permission, TextComponent... messages)
    {
        if (Array.isEmpty(messages))
        {
            return;
        }

        Players.streamAllWithPerm(permission)
               .forEach(p -> to(p, messages));
    }

    @Override
    public <C extends Collection<TextComponent>> void toAllWithPerm(@NonNull String permission, C collection)
    {
        if (Array.isEmpty(collection))
        {
            return;
        }

        Players.streamAllWithPerm(permission)
               .forEach(p -> to(p, collection));
    }

    @Override
    public void toAllWithPermExcept(@NonNull String permission, @NonNull Player exclude, TextComponent... messages)
    {
        if (Array.isEmpty(messages))
        {
            return;
        }

        Players.streamAllWithPerm(permission)
               .filter(p -> !p.equals(exclude))
               .forEach(p -> to(p, messages));
    }

    @Override
    public <P extends Collection<Player>> void toAllWithPermExcept(@NonNull String permission,
                                                                   @NonNull P exclude,
                                                                   TextComponent... messages)
    {
        if (Array.isEmpty(messages))
        {
            return;
        }

        Players.streamAllWithPerm(permission)
               .filter(p -> !exclude.contains(p))
               .forEach(p -> to(p, messages));
    }

    @Override
    public <P extends Collection<Player>, C extends Collection<TextComponent>> void toAllWithPermExcept(@NonNull String permission,
                                                                                                        @NonNull P exclude,
                                                                                                        C collection)
    {
        if (Array.isEmpty(collection))
        {
            return;
        }

        Players.streamAllWithPerm(permission)
               .filter(p -> !exclude.contains(p))
               .forEach(p -> to(p, collection));
    }

}
