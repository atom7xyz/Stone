package xyz.sorridi.stone;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.mikigal.config.BukkitConfiguration;
import pl.mikigal.config.serializer.Serializer;
import xyz.sorridi.stone.annotations.impl.serializer.RegisterSerializer;
import xyz.sorridi.stone.immutable.Err;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Serializer for {@link PotionEffect}.
 *
 * @author Sorridi
 * @since 1.0
 */
@RegisterSerializer(of = PotionEffect.class)
public class PotionEffectSerializer extends Serializer<PotionEffect>
{

    @Override
    protected void saveObject(String path, PotionEffect object, BukkitConfiguration configuration)
    {
        // In saveObject() method you have to set data of object to config. You can use set() method to set another
        // object which need serialization too
        configuration.set(path + ".type", object.getType().getName());
        configuration.set(path + ".duration", object.getDuration());
        configuration.set(path + ".amplifier", object.getAmplifier());
    }

    @Override
    public PotionEffect deserialize(String path, BukkitConfiguration configuration)
    {
        // In deserialize() method you have to load data from config and return instance of object
        String type = configuration.getString(path + ".type");
        int duration = configuration.getInt(path + ".duration");
        int amplifier = configuration.getInt(path + ".amplifier");

        checkNotNull(type, Err.NULL.expect(path + ".type"));

        PotionEffectType effectType = PotionEffectType.getByName(type);
        checkNotNull(effectType, Err.NULL.expect(PotionEffectType.class));

        return new PotionEffect(effectType, duration, amplifier);
    }

}
