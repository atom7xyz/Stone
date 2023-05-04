package xyz.sorridi.stone.utils;

import lombok.NonNull;
import xyz.sorridi.stone.immutable.ErrorMessages;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Replacements utilities for strings.
 * @author Sorridi
 * @since 1.0
 */
public class Replace
{

    /**
     * Replaces the target with the object in the string.
     * @param what The string to replace.
     * @param target The target to replace.
     * @param with The object to replace with.
     * @return The replaced string.
     */
    public static <T> String of(@NonNull String what, @NonNull String target, @NonNull T with)
    {
        return what.replace(target, with.toString());
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

        for (int i = 0; i < target.length; i++)
        {
            what = what.replace(target[i], with[i].toString());
        }

        return what;
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
        return Arrays.stream(what).map(s -> s.replace(target, with.toString())).toArray(String[]::new);
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

        for (int i = 0; i < what.length; i++)
        {
            for (int j = 0; j < target.length; j++)
            {
                what[i] = what[i].replace(target[j], with[j].toString());
            }
        }

        return what;
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
        return (W) what.stream().map(s -> s.replace(target, with.toString())).collect(Collectors.toList());
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

        return (W) what.stream().map(s ->
        {
            for (int i = 0; i < target.length; i++)
            {
                s = s.replace(target[i], with[i].toString());
            }

            return s;
        }).collect(Collectors.toList());
    }

}
