package xyz.sorridi.stone.utils;

import lombok.NonNull;
import lombok.val;
import xyz.sorridi.stone.immutable.ErrorMessages;

import java.util.Arrays;
import java.util.Collection;
import java.util.WeakHashMap;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Replacements utilities for strings.
 * @author Sorridi
 * @since 1.0
 */
public class Replace
{
    public static final
            WeakHashMap<String,
            WeakHashMap<String,
            WeakHashMap<Object, String>>> SINGLE_RES_CACHE;

    public static final
            WeakHashMap<String,
            WeakHashMap<String[],
            WeakHashMap<Object[], String>>> MULTI_RES_CACHE;

    public static final
            WeakHashMap<String[],
            WeakHashMap<String,
            WeakHashMap<Object, String[]>>> MULTI_CACHE_ARR_1;

    public static final
            WeakHashMap<String[],
            WeakHashMap<String[],
            WeakHashMap<Object, String[]>>> MULTI_CACHE_ARR_2;

    public static final
            WeakHashMap<Collection<String>,
            WeakHashMap<String,
            WeakHashMap<Object, Collection<String>>>> MULTI_CACHE_COLL_1;

    public static final
            WeakHashMap<Collection<String>,
            WeakHashMap<String[],
            WeakHashMap<Object[], Collection<String>>>> MULTI_CACHE_COLL_2;

    static
    {
        SINGLE_RES_CACHE    = new WeakHashMap<>();
        MULTI_RES_CACHE     = new WeakHashMap<>();
        MULTI_CACHE_ARR_1   = new WeakHashMap<>();
        MULTI_CACHE_ARR_2   = new WeakHashMap<>();
        MULTI_CACHE_COLL_1  = new WeakHashMap<>();
        MULTI_CACHE_COLL_2  = new WeakHashMap<>();
    }

    /**
     * Replaces the target with the object in the string.
     * @param what The string to replace.
     * @param target The target to replace.
     * @param with The object to replace with.
     * @return The replaced string.
     */
    public static <T> String of(@NonNull String what, @NonNull String target, @NonNull T with)
    {
        if (SINGLE_RES_CACHE.containsKey(what))
        {
            val _what = SINGLE_RES_CACHE.get(what);

            if (_what.containsKey(target))
            {
                val _target = _what.get(target);

                if (_target.containsKey(with))
                {
                    return _target.get(with);
                }
            }
        }

        String result = what.replace(target, with.toString());

        SINGLE_RES_CACHE
                .computeIfAbsent(what, k -> new WeakHashMap<>())
                .computeIfAbsent(target, k -> new WeakHashMap<>())
                .putIfAbsent(with, result);

        return result;
    }

    /**
     * Replaces the target with the object in the string.
     * @param what The string to replace.
     * @param target The target to replace.
     * @param with The object to replace with.
     * @return The replaced string.
     */
    @SafeVarargs
    public static <T> String of(@NonNull String what, @NonNull String[] target, @NonNull T... with)
    {
        checkArgument(target.length == with.length, ErrorMessages.ARGS_NOT_SAME_SIZE.get());

        if (MULTI_RES_CACHE.containsKey(what))
        {
            val _what = MULTI_RES_CACHE.get(what);

            if (_what.containsKey(target))
            {
                val _target = _what.get(target);

                if (_target.containsKey(with))
                {
                    return _target.get(with);
                }
            }
        }

        String result = what;

        for (int i = 0; i < target.length; i++)
        {
            result = result.replace(target[i], with[i].toString());
        }

        MULTI_RES_CACHE
                .computeIfAbsent(what, k -> new WeakHashMap<>())
                .computeIfAbsent(target, k -> new WeakHashMap<>())
                .putIfAbsent(with, result);

        return result;
    }

    /**
     * Replaces the target with the object in the array of strings.
     * @param what The array of strings to replace.
     * @param target The target to replace.
     * @param with The object to replace with.
     * @return The replaced array of strings.
     */
    public static <T> String[] of(@NonNull String[] what, @NonNull String target, @NonNull T with)
    {
        if (MULTI_CACHE_ARR_1.containsKey(what))
        {
            val _what = MULTI_CACHE_ARR_1.get(what);

            if (_what.containsKey(target))
            {
                val _target = _what.get(target);

                if (_target.containsKey(with))
                {
                    return _target.get(with);
                }
            }
        }

        String[] result = Arrays.stream(what).map(s -> s.replace(target, with.toString())).toArray(String[]::new);

        MULTI_CACHE_ARR_1
                .computeIfAbsent(what, k -> new WeakHashMap<>())
                .computeIfAbsent(target, k -> new WeakHashMap<>())
                .putIfAbsent(with, result);

        return result;
    }

    /**
     * Replaces the array of targets with the object(s) in the array of strings.
     * @param what The array of strings to replace.
     * @param target The array of targets to replace.
     * @param with The array of objects to replace with.
     * @return The replaced array of strings.
     */
    @SafeVarargs
    public static <T> String[] of(@NonNull String[] what, @NonNull String[] target, @NonNull T... with)
    {
        checkArgument(target.length == with.length, ErrorMessages.ARGS_NOT_SAME_SIZE.get());

        if (MULTI_CACHE_ARR_2.containsKey(what))
        {
            val _what = MULTI_CACHE_ARR_2.get(what);

            if (_what.containsKey(target))
            {
                val _target = _what.get(target);

                if (_target.containsKey(with))
                {
                    return _target.get(with);
                }
            }
        }

        String[] result = what;

        for (int i = 0; i < result.length; i++)
        {
            for (int j = 0; j < target.length; j++)
            {
                result[i] = result[i].replace(target[j], with[j].toString());
            }
        }

        MULTI_CACHE_ARR_2
                .computeIfAbsent(what, k -> new WeakHashMap<>())
                .computeIfAbsent(target, k -> new WeakHashMap<>())
                .putIfAbsent(with, result);

        return result;
    }

    /**
     * Replaces the target with the object in the collection of strings.
     * @param what The collection of strings to replace.
     * @param target The target to replace.
     * @param with The object to replace with.
     * @return The replaced collection of strings.
     */
    @SuppressWarnings("unchecked")
    public static <W extends Collection<String>, T> W of(@NonNull W what, @NonNull String target, @NonNull T with)
    {
        if (MULTI_CACHE_COLL_1.containsKey(what))
        {
            val _what = MULTI_CACHE_COLL_1.get(what);

            if (_what.containsKey(target))
            {
                val _target = _what.get(target);

                if (_target.containsKey(with))
                {
                    return (W) _target.get(with);
                }
            }
        }

        W result = (W) what.stream().map(s -> s.replace(target, with.toString())).toList();

        MULTI_CACHE_COLL_1
                .computeIfAbsent(what, k -> new WeakHashMap<>())
                .computeIfAbsent(target, k -> new WeakHashMap<>())
                .putIfAbsent(with, result);

        return result;
    }

    /**
     * Replaces the array of targets with the object(s) in the collection of strings.
     * @param what The collection of strings to replace.
     * @param target The array of targets to replace.
     * @param with The array of objects to replace with.
     * @return The replaced collection of strings.
     */
    @SuppressWarnings("unchecked")
    public static <W extends Collection<String>, T> W of(@NonNull W what, @NonNull String[] target, @NonNull T... with)
    {
        checkArgument(target.length == with.length, ErrorMessages.ARGS_NOT_SAME_SIZE.get());

        if (MULTI_CACHE_COLL_2.containsKey(what))
        {
            val _what = MULTI_CACHE_COLL_2.get(what);

            if (_what.containsKey(target))
            {
                val _target = _what.get(target);

                if (_target.containsKey(with))
                {
                    return (W) _target.get(with);
                }
            }
        }

        W result = (W) what.stream().map(s ->
        {
            for (int i = 0; i < target.length; i++)
            {
                s = s.replace(target[i], with[i].toString());
            }

            return s;
        }).toList();

        MULTI_CACHE_COLL_2
                .computeIfAbsent(what, k -> new WeakHashMap<>())
                .computeIfAbsent(target, k -> new WeakHashMap<>())
                .putIfAbsent(with, result);

        return result;
    }

}
