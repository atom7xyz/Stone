package xyz.sorridi.stone.velocity.utils;

import com.velocitypowered.api.proxy.Player;
import xyz.sorridi.stone.velocity.StoneVelocity;

import java.util.stream.Stream;

public class Players
{
    private static StoneVelocity server;

    public static void setServer(StoneVelocity server)
    {
        Players.server = server;
    }

    public static Stream<Player> stream()
    {
        return server.getServer()
                     .getAllPlayers()
                     .stream();
    }

}
