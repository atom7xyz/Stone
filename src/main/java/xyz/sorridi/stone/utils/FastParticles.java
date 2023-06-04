package xyz.sorridi.stone.utils;

import fr.mrmicky.fastparticles.ParticleData;
import fr.mrmicky.fastparticles.ParticleType;
import lombok.NonNull;
import lombok.val;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;

import java.util.WeakHashMap;

public class FastParticles
{
    private static final
            WeakHashMap<Material, ParticleData> PARTICLE_DATA_CACHE;

    private static final
            WeakHashMap<Material,
            WeakHashMap<Byte, ParticleData>> PARTICLE_DATA_FULL_CACHE;

    private static final
            WeakHashMap<Particle, ParticleType> PARTICLE_TYPE_CACHE;

    static
    {
        PARTICLE_DATA_CACHE         = new WeakHashMap<>();
        PARTICLE_DATA_FULL_CACHE    = new WeakHashMap<>();
        PARTICLE_TYPE_CACHE         = new WeakHashMap<>();
    }

    /**
     * Get the particle data from a material.
     * @param material The material to get the particle data.
     * @return The particle data.
     */
    public static ParticleData getParticleData(Material material)
    {
        ParticleData particleData;

        if (PARTICLE_DATA_CACHE.containsKey(material))
        {
            return PARTICLE_DATA_CACHE.get(material);
        }

        particleData = ParticleData.createBlockData(material);
        PARTICLE_DATA_CACHE.putIfAbsent(material, particleData);

        return particleData;
    }

    /**
     * Get the particle data from a block.
     * @param block The block to get the particle data.
     * @return The particle data.
     */
    public static ParticleData getParticleData(@NonNull Block block)
    {
        return getParticleData(block.getType(), block.getData());
    }

    /**
     * Get the particle data from a material and data.
     * @param material The material to get the particle data.
     * @param data The data to get the particle data.
     * @return The particle data.
     */
    public static ParticleData getParticleData(Material material, byte data)
    {
        ParticleData particleData;

        if (PARTICLE_DATA_FULL_CACHE.containsKey(material))
        {
            val _dataMap = PARTICLE_DATA_FULL_CACHE.get(material);

            if (_dataMap.containsKey(data))
            {
                return _dataMap.get(data);
            }
        }

        particleData = ParticleData.createBlockData(material, data);

        PARTICLE_DATA_FULL_CACHE
                .computeIfAbsent(material, k -> new WeakHashMap<>())
                .putIfAbsent(data, particleData);

        return particleData;
    }

    /**
     * Get the particle type from a particle.
     * @param particle The particle to get the particle type.
     * @return The particle type.
     */
    public static ParticleType of(Particle particle)
    {
        if (PARTICLE_TYPE_CACHE.containsKey(particle))
        {
            return PARTICLE_TYPE_CACHE.get(particle);
        }

        ParticleType result = ParticleType.of(particle.name());

        PARTICLE_TYPE_CACHE.putIfAbsent(particle, result);

        return result;
    }

}
