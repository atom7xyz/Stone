package xyz.sorridi.stone.data;

import java.util.HashMap;
import java.util.stream.Stream;

/**
 * An HashMap with a single value.
 * @param <K> The key and the value of the Map.
 */
public class SingleHashMap<K> extends HashMap<K, K>
{

    public K put(K key)
    {
        return put(key, key);
    }

    public Stream<K> stream()
    {
        return keySet().stream();
    }

    public Stream<K> parallelStream()
    {
        return keySet().parallelStream();
    }

    public boolean contains(K key)
    {
        return containsKey(key);
    }

}