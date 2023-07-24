package xyz.sorridi.stone.spigot.annotations.serializer;

import org.bukkit.plugin.Plugin;
import pl.mikigal.config.ConfigAPI;
import pl.mikigal.config.serializer.Serializer;
import xyz.sorridi.stone.common.annotations.IProcessor;
import xyz.sorridi.stone.common.annotations.ResourceGatherer;
import xyz.sorridi.stone.common.immutable.Err;
import xyz.sorridi.stone.common.utils.constructor.ConstructorCaller;

import java.lang.annotation.ElementType;
import java.util.logging.Logger;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Registers custom serializers present in a given plugin.
 *
 * @author Sorridi
 * @since 1.0
 */
public class SerializerProcessor implements IProcessor
{
    private final Plugin plugin;
    private final Logger logger;

    public SerializerProcessor(Plugin plugin)
    {
        this.plugin = plugin;
        this.logger = plugin.getLogger();
    }

    @Override
    public void process()
    {
        logger.info("Processing serializers...");

        ResourceGatherer.forEachAnnotation(RegisterSerializer.class, (annotation, found) ->
        {
            var foundClass = (Class<?>) found;

            logger.info("Found serializer: " + foundClass.getName());

            var of = checkNotNull(annotation.of(), Err.NULL.get());

            ConstructorCaller
                    .call(foundClass)
                    .ifPresent(serializer ->
                               {
                                   ConfigAPI.registerSerializer(of, (Serializer<?>) serializer);
                                   logger.info("Registered serializer for: " + of.getName());
                               });

        }, ElementType.TYPE, plugin.getClass());

        logger.info("Processed serializers!");
    }

}
