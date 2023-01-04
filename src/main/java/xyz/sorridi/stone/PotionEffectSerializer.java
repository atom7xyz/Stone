package xyz.sorridi.stone;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.mikigal.config.BukkitConfiguration;
import pl.mikigal.config.serializer.Serializer;
import xyz.sorridi.stone.annotations.impl.serializer.RegisterSerializer;
import xyz.sorridi.stone.immutable.ErrorMessages;

import static com.google.common.base.Preconditions.checkNotNull;

@RegisterSerializer(of = PotionEffect.class)
public class PotionEffectSerializer extends Serializer<PotionEffect>
{

    @Override
    protected void saveObject(String path, PotionEffect object, BukkitConfiguration configuration)
    {
        // In saveObject() method you have to set data of object to config. You can use set() method to set another object which need serialization too
        configuration.set(path + ".type", object.getType().getName());
        configuration.set(path + ".duration", object.getDuration());
        configuration.set(path + ".amplifier", object.getAmplifier());
    }

    @Override
    public PotionEffect deserialize(String path, BukkitConfiguration configuration)
    {
        // In deserialize() method you have to load data from config and return instance of object
        PotionEffectType type = PotionEffectType.getByName(configuration.getString(path + ".type"));
        int duration = configuration.getInt(path + ".duration");
        int amplifier = configuration.getInt(path + ".amplifier");

        checkNotNull(type, ErrorMessages.NULL.expected(PotionEffectType.class));

        return new PotionEffect(type, duration, amplifier);
    }

}
