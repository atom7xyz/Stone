package xyz.sorridi.stone.common.data.structures;

import lombok.NonNull;
import org.jetbrains.annotations.Nullable;

import java.lang.ref.SoftReference;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * A {@link ConcurrentHashMap} implementation with {@link SoftReference} values.
 *
 * @param <K> The key of the Map.
 * @param <V> The value of the Map.
 * @author Sorridi
 * @since 1.0
 */
public class SoftMap<K, V>
{
    private final ConcurrentHashMap<K, SoftReference<V>> internal;

    public SoftMap()
    {
        this.internal = new ConcurrentHashMap<>();
        SoftCleaner.add(this);
    }

    /**
     * Puts a value in the Map.
     * <br>
     * <br>
     * If the value pointed by the {@link SoftReference} is null, it will be replaced instead.
     *
     * @param key   The key of the Map.
     * @param value The value of the Map.
     * @return The value of the Map.
     */
    public SoftReference<V> put(@NonNull K key, @NonNull V value)
    {
        var soft = internal.get(key);
        var newSoft = new SoftReference<>(value);

        if (soft != null && soft.get() == null)
        {
            return internal.replace(key, newSoft);
        }

        return internal.put(key, newSoft);
    }

    /**
     * Puts a value in the Map if the key is not present.
     * <br>
     * <br>
     * If the value pointed by the {@link SoftReference} is null, it will be replaced instead.
     *
     * @param key   The key of the Map.
     * @param value The value of the Map.
     * @return The value of the Map.
     */
    public SoftReference<V> putIfAbsent(@NonNull K key, @NonNull V value)
    {
        var soft = internal.get(key);

        if (containsKey(key))
        {
            return soft;
        }

        var newSoft = new SoftReference<>(value);

        if (soft != null && soft.get() == null)
        {
            return internal.replace(key, newSoft);
        }

        return internal.putIfAbsent(key, newSoft);
    }

    /**
     * Puts a value in the Map.
     * <br>
     * <br>
     * If the value pointed by the {@link SoftReference} is null, it will be replaced instead.
     *
     * @param key   The key of the Map.
     * @param value The value of the Map.
     * @return The value of the Map.
     */
    public SoftReference<V> put(@NonNull K key, @NonNull SoftReference<V> value)
    {
        var soft = internal.get(key);

        if (soft != null && soft.get() == null)
        {
            return internal.replace(key, value);
        }

        return internal.put(key, value);
    }

    /**
     * Puts a value in the Map if the key is not present.
     * <br>
     * <br>
     * If the value pointed by the {@link SoftReference} is null, it will be replaced instead.
     *
     * @param key   The key of the Map.
     * @param value The value of the Map.
     * @return The value of the Map.
     */
    public SoftReference<V> putIfAbsent(@NonNull K key, @NonNull SoftReference<V> value)
    {
        var soft = internal.get(key);

        if (containsKey(key))
        {
            return soft;
        }

        if (soft != null && soft.get() == null)
        {
            return internal.replace(key, value);
        }

        return internal.putIfAbsent(key, value);
    }

    /**
     * Gets a value from the Map.
     *
     * @param key The key of the Map.
     * @return The value of the Map.
     */
    @Nullable
    public V get(@NonNull K key)
    {
        var soft = getSoft(key);
        return soft != null ? soft.get() : null;
    }

    /**
     * Gets a value from the Map.
     *
     * @param key The key of the Map.
     * @return The value of the Map.
     */
    @Nullable
    public SoftReference<V> getSoft(@NonNull K key)
    {
        return internal.get(key);
    }

    /**
     * Removes an entry from the Map.
     *
     * @param key The key of the Map.
     */
    public void remove(@NonNull K key)
    {
        internal.remove(key);
    }

    /**
     * Computes a value if the key is not present.
     *
     * @param key     The key of the Map.
     * @param compute The computation.
     * @return The value of the Map.
     */
    public V computeIfAbsent(@NonNull K key, @NonNull Function<? super K, ? extends V> compute)
    {
        return internal.computeIfAbsent(key, k -> new SoftReference<>(compute.apply(k))).get();
    }

    /**
     * Replaces a value in the Map.
     *
     * @param key   The key of the Map.
     * @param value The value of the Map.
     * @return The value of the Map.
     */
    public SoftReference<V> replace(@NonNull K key, @NonNull V value)
    {
        return replace(key, new SoftReference<>(value));
    }

    /**
     * Replaces a value in the Map.
     *
     * @param key   The key of the Map.
     * @param value The value of the Map.
     * @return The value of the Map.
     */
    public SoftReference<V> replace(@NonNull K key, @NonNull SoftReference<V> value)
    {
        return internal.replace(key, value);
    }

    /**
     * Checks if the Map contains the key.
     *
     * @param key The key of the Map.
     * @return If the Map contains the key.
     */
    public boolean containsKey(@NonNull K key)
    {
        return internal.containsKey(key);
    }

    /**
     * Gets the size of the Map.
     *
     * @return The size of the Map.
     */
    public int size()
    {
        return internal.size();
    }

    /**
     * Gets the set of entries of the Map.
     *
     * @return The set of entries of the Map.
     */
    public Set<Map.Entry<K, SoftReference<V>>> entrySet()
    {
        return internal.entrySet();
    }

    /**
     * Gets the keys of the Map.
     *
     * @return The keys of the Map.
     */
    public Set<K> keySet()
    {
        return internal.keySet();
    }

    /**
     * Gets the values of the Map.
     *
     * @return The values of the Map.
     */
    public Collection<SoftReference<V>> values()
    {
        return internal.values();
    }

    /**
     * Streams the Map.
     *
     * @return The stream of entries of the Map.
     */
    public Stream<Map.Entry<K, SoftReference<V>>> stream()
    {
        return entrySet().stream();
    }

    /**
     * Streams the keys of the Map.
     *
     * @return The stream of keys of the Map.
     */
    public Stream<K> streamKeys()
    {
        return keySet().stream();
    }

    /**
     * Streams the values of the Map.
     *
     * @return The stream of values of the Map.
     */
    public Stream<SoftReference<V>> streamValues()
    {
        return values().stream();
    }

    /**
     * Cleans the Map.
     *
     * @return The number of entries removed.
     */
    public int clean()
    {
        AtomicInteger count = new AtomicInteger();

        entrySet().removeIf(entry ->
                            {
                                var soft = entry.getValue();

                                if (soft.get() == null)
                                {
                                    count.getAndIncrement();
                                    return true;
                                }

                                return false;
                            });

        return count.get();
    }

    /**
     * Clears the Map.
     */
    public void clear()
    {
        internal.clear();
    }

    /**
     * Handler of the Map.
     *
     * @return The handler of the Map.
     */
    public ConcurrentHashMap<K, SoftReference<V>> handle()
    {
        return internal;
    }

}
