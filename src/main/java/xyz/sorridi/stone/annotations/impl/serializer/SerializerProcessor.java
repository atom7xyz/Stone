package xyz.sorridi.stone.annotations.impl.serializer;

import lombok.val;
import org.bukkit.plugin.Plugin;
import pl.mikigal.config.ConfigAPI;
import pl.mikigal.config.serializer.Serializer;
import xyz.sorridi.stone.annotations.IProcessor;
import xyz.sorridi.stone.annotations.ResourceGatherer;
import xyz.sorridi.stone.immutable.ErrorMessages;
import xyz.sorridi.stone.utils.constructor.ConstructorCaller;

import java.lang.annotation.ElementType;
import java.util.logging.Logger;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Registers custom serializers present in a given plugin.
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

        ResourceGatherer.forEachAnnotationInTypes(RegisterSerializer.class, (annotation, found) ->
        {
            logger.info("Found serializer: " + found);

            val toRegister = (RegisterSerializer) annotation;
            val foundClass = (Class<?>) found;

            val of = checkNotNull(toRegister.of(), ErrorMessages.NULL);

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
