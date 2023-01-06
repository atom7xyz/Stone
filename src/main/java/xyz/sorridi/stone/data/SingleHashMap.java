package xyz.sorridi.stone.data;

import java.util.HashMap;
import java.util.stream.Stream;

/**
 * An HashMap with a single value.
 * @param <K> The key and the value of the Map.
 */
public class SingleHashMap<K> extends HashMap<K, K>
{

    /**
     * Puts a value in the Map.
     * @param key The key and the value of the Map.
     * @return The value of the Map.
     */
    public K put(K key)
    {
        return put(key, key);
    }

    /**
     * Returns a stream of the keys.
     * @return The key of the Map.
     */
    public Stream<K> stream()
    {
        return keySet().stream();
    }

    /**
     * Returns a parallel stream of the keys.
     * @return The value of the Map.
     */
    public Stream<K> parallelStream()
    {
        return keySet().parallelStream();
    }

    /**
     * Checks if the key is present in the Map.
     * @param key The key of the Map.
     * @return If the key is present.
     */
    public boolean contains(K key)
    {
        return containsKey(key);
    }

}