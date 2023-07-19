package xyz.sorridi.stone.utils;

import lombok.NonNull;
import xyz.sorridi.stone.data.structures.SoftMap;
import xyz.sorridi.stone.immutable.Err;
import xyz.sorridi.stone.utils.data.Array;

import java.util.Arrays;
import java.util.Collection;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Replacements utilities for strings.
 *
 * @author Sorridi
 * @since 1.0
 */
public class Replace
{
    public static final SoftMap<Array.Wrapper, Object> CACHE;

    static
    {
        CACHE = new SoftMap<>();
    }

    /**
     * Replaces the target with the object in the string.
     *
     * @param what   The string to replace.
     * @param target The target to replace.
     * @param with   The object to replace with.
     * @return The replaced string.
     */
    public static <T> String of(@NonNull String what, @NonNull String target, @NonNull T with)
    {
        var key = new Array.Wrapper(what, target, with);
        var val = CACHE.get(key);

        if (val != null)
        {
            return (String) val;
        }

        String result = what.replace(target, with.toString());

        CACHE.putIfAbsent(key, result);

        return result;
    }

    /**
     * Replaces the target with the object in the string.
     *
     * @param what   The string to replace.
     * @param target The target to replace.
     * @param with   The object to replace with.
     * @return The replaced string.
     */
    @SafeVarargs
    public static <T> String of(@NonNull String what, @NonNull String[] target, @NonNull T... with)
    {
        checkArgument(target.length == with.length, Err.ARGS_NOT_SAME_SIZE.get());

        var key = new Array.Wrapper(what, target, with);
        var val = CACHE.get(key);

        if (val != null)
        {
            return (String) val;
        }

        String result = what;

        for (int i = 0; i < target.length; i++)
        {
            result = result.replace(target[i], with[i].toString());
        }

        CACHE.putIfAbsent(key, result);

        return result;
    }

    /**
     * Replaces the target with the object in the array of strings.
     *
     * @param what   The array of strings to replace.
     * @param target The target to replace.
     * @param with   The object to replace with.
     * @return The replaced array of strings.
     */
    public static <T> String[] of(@NonNull String[] what, @NonNull String target, @NonNull T with)
    {
        var key = new Array.Wrapper(what, target, with);
        var val = CACHE.get(key);

        if (val != null)
        {
            return (String[]) val;
        }

        String[] result = Arrays.stream(what)
                                .map(s -> s.replace(target, with.toString()))
                                .toArray(String[]::new);

        CACHE.putIfAbsent(key, result);

        return result;
    }

    /**
     * Replaces the array of targets with the object(s) in the array of strings.
     *
     * @param what   The array of strings to replace.
     * @param target The array of targets to replace.
     * @param with   The array of objects to replace with.
     * @return The replaced array of strings.
     */
    @SafeVarargs
    public static <T> String[] of(@NonNull String[] what, @NonNull String[] target, @NonNull T... with)
    {
        checkArgument(target.length == with.length, Err.ARGS_NOT_SAME_SIZE.get());

        var key = new Array.Wrapper(what, target, with);
        var val = CACHE.get(key);

        if (val != null)
        {
            return (String[]) val;
        }

        String[] result = what.clone();

        for (int i = 0; i < result.length; i++)
        {
            for (int j = 0; j < target.length; j++)
            {
                result[i] = result[i].replace(target[j], with[j].toString());
            }
        }

        CACHE.putIfAbsent(key, result);

        return result;
    }

    /**
     * Replaces the target with the object in the collection of strings.
     *
     * @param what   The collection of strings to replace.
     * @param target The target to replace.
     * @param with   The object to replace with.
     * @return The replaced collection of strings.
     */
    @SuppressWarnings("unchecked")
    public static <W extends Collection<String>, T> W of(@NonNull W what, @NonNull String target, @NonNull T with)
    {
        var key = new Array.Wrapper(what, target, with);
        var val = CACHE.get(key);

        if (val != null)
        {
            return (W) val;
        }

        W result = (W) what.stream()
                           .map(s -> s.replace(target, with.toString()))
                           .toList();

        CACHE.putIfAbsent(key, result);

        return result;
    }

    /**
     * Replaces the array of targets with the object(s) in the collection of strings.
     *
     * @param what   The collection of strings to replace.
     * @param target The array of targets to replace.
     * @param with   The array of objects to replace with.
     * @return The replaced collection of strings.
     */
    @SuppressWarnings("unchecked")
    public static <W extends Collection<String>, T> W of(@NonNull W what, @NonNull String[] target, @NonNull T... with)
    {
        checkArgument(target.length == with.length, Err.ARGS_NOT_SAME_SIZE.get());

        var key = new Array.Wrapper(what, target, with);
        var val = CACHE.get(key);

        if (val != null)
        {
            return (W) val;
        }

        W result = (W) what.stream()
                           .map(s ->
                                {
                                    for (int i = 0; i < target.length; i++)
                                    {
                                        s = s.replace(target[i], with[i].toString());
                                    }
                                    return s;
                                })
                           .toList();

        CACHE.putIfAbsent(key, result);

        return result;
    }

    /**
     * Gets the rough size of the cache.
     *
     * @return The rough size of the cache.
     */
    public static int getCacheSize()
    {
        return CACHE.size();
    }

}
