package xyz.sorridi.stone.common.data.structures;

import lombok.NonNull;

import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * A ConcurrentHashMap with a single value.
 *
 * @param <K> The key and the value of the Map.
 * @author Sorridi
 * @since 1.0
 */
public class SingleConcurrentMap<K> extends ConcurrentHashMap<K, K>
{

    /**
     * Puts a value in the Map.
     *
     * @param key The key and the value of the Map.
     * @return The value of the Map.
     */
    public K put(@NonNull K key)
    {
        return put(key, key);
    }

    /**
     * Puts a value in the Map if the key is not present.
     *
     * @param key The key and the value of the Map.
     * @return The value of the Map.
     */
    public K putIfAbsent(@NonNull K key)
    {
        return putIfAbsent(key, key);
    }

    /**
     * Returns a stream of the keys.
     *
     * @return The key of the Map.
     */
    public Stream<K> stream()
    {
        return keySet().stream();
    }

    /**
     * Returns a parallel stream of the keys.
     *
     * @return The value of the Map.
     */
    public Stream<K> parallelStream()
    {
        return keySet().parallelStream();
    }

    /**
     * Checks if the key is present in the Map.
     *
     * @param key The key of the Map.
     * @return If the key is present.
     */
    public boolean contains(@NonNull Object key)
    {
        return containsKey(key);
    }

}