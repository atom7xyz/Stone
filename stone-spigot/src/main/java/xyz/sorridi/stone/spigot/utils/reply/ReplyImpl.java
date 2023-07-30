package xyz.sorridi.stone.spigot.utils.reply;

import lombok.NonNull;
import me.lucko.helper.text3.Text;
import me.lucko.helper.utils.Players;
import org.bukkit.entity.Player;
import xyz.sorridi.stone.common.utils.IReply;
import xyz.sorridi.stone.common.utils.data.Array;

import java.util.Collection;
import java.util.HashSet;

/**
 * {@link Reply} implementation for Spigot.
 *
 * @author Sorridi
 * @since 1.0
 */
public class ReplyImpl implements IReply<Player, String>
{

    @Override
    public boolean to(Player player, String... messages)
    {
        if (player == null || Array.isEmpty(messages))
        {
            return false;
        }

        if (!player.isOnline())
        {
            return false;
        }

        for (String message : messages)
        {
            if (!message.isEmpty())
            {
                player.sendMessage(Text.colorize(message));
            }
        }

        return true;
    }

    @Override
    public <C extends Collection<String>> boolean to(Player player, C collection)
    {
        if (!player.isOnline() || Array.isEmpty(collection))
        {
            return false;
        }

        collection.stream()
                  .filter(message -> !message.isEmpty())
                  .forEach(message -> player.sendMessage(Text.colorize(message)));

        return true;
    }

    @Override
    public void toAll(String... messages)
    {
        to(Players.all(), messages);
    }

    @Override
    public <P extends Collection<String>> void toAll(P collection)
    {
        to(Players.all(), collection);
    }

    @Override
    public void toAllExcept(@NonNull Player exclude, String... messages)
    {
        if (Array.isEmpty(messages))
        {
            return;
        }

        Players.stream()
               .filter(p -> !p.equals(exclude))
               .forEach(p -> to(p, messages));
    }

    @Override
    public <C extends Collection<String>> void toAllExcept(@NonNull Player toExclude, C collection)
    {
        if (Array.isEmpty(collection))
        {
            return;
        }

        Players.stream()
               .filter(p -> !p.equals(toExclude))
               .forEach(p -> to(p, collection));
    }

    @Override
    public <P extends Collection<Player>> void toAllExcept(@NonNull P toExclude, String... messages)
    {
        if (Array.isEmpty(messages))
        {
            return;
        }

        HashSet<Player> excluded = new HashSet<>(toExclude);

        Players.stream()
               .filter(p -> !excluded.contains(p))
               .forEach(p -> to(p, messages));
    }

    @Override
    public <P extends Collection<Player>, C extends Collection<String>> void toAllExceptMulti(@NonNull P toExclude,
                                                                                              C collection)
    {
        if (Array.isEmpty(collection))
        {
            return;
        }

        HashSet<Player> excluded = new HashSet<>(toExclude);

        Players.stream()
               .filter(p -> !excluded.contains(p))
               .forEach(p -> to(p, collection));
    }

    @Override
    public <P extends Collection<Player>> void toExcept(P players, @NonNull P exclude, String... messages)
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
    public <P extends Collection<Player>, C extends Collection<String>> void toExcept(P players,
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
    public <P extends Collection<Player>> void toExcept(P players, @NonNull Player exclude, String... messages)
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
    public <P extends Collection<Player>, C extends Collection<String>> void toExcept(P players,
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
    public void toAllWithPerm(@NonNull String permission, String... messages)
    {
        if (Array.isEmpty(messages))
        {
            return;
        }

        Players.stream()
               .filter(p -> p.hasPermission(permission))
               .forEach(p -> to(p, messages));
    }

    @Override
    public <C extends Collection<String>> void toAllWithPerm(@NonNull String permission, C collection)
    {
        if (Array.isEmpty(collection))
        {
            return;
        }

        Players.stream()
               .filter(p -> p.hasPermission(permission))
               .forEach(p -> to(p, collection));
    }

    @Override
    public void toAllWithPermExcept(@NonNull String permission, @NonNull Player exclude, String... messages)
    {
        if (Array.isEmpty(messages))
        {
            return;
        }

        Players.stream()
               .filter(p -> p.hasPermission(permission) && !p.equals(exclude))
               .forEach(p -> to(p, messages));
    }

    @Override
    public <P extends Collection<Player>> void toAllWithPermExcept(@NonNull String permission,
                                                                   @NonNull P exclude,
                                                                   String... messages)
    {
        if (Array.isEmpty(messages))
        {
            return;
        }

        Players.stream()
               .filter(p -> p.hasPermission(permission) && !exclude.contains(p))
               .forEach(p -> to(p, messages));
    }

    @Override
    public <P extends Collection<Player>, C extends Collection<String>> void toAllWithPermExcept(@NonNull String permission,
                                                                                                 @NonNull P exclude,
                                                                                                 C collection)
    {
        if (Array.isEmpty(collection))
        {
            return;
        }

        Players.stream()
               .filter(p -> p.hasPermission(permission) && !exclude.contains(p))
               .forEach(p -> to(p, collection));
    }

}
