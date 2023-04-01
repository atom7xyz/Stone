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

    public static <T> String of(@NonNull String what, @NonNull String target, @NonNull T with)
    {
        return what.replaceAll(target, with.toString());
    }

    @SafeVarargs
    public static <T> String of(@NonNull String what, @NonNull String[] target, @NonNull T... with)
    {
        checkArgument(target.length == with.length, ErrorMessages.ARGS_NOT_SAME_SIZE.get());

        for (int i = 0; i < target.length; i++)
        {
            what = what.replaceAll(target[i], with[i].toString());
        }

        return what;
    }

    public static <T> String[] of(@NonNull String[] what, @NonNull String target, @NonNull T with)
    {
        return Arrays.stream(what).map(s -> s.replaceAll(target, with.toString())).toArray(String[]::new);
    }

    @SafeVarargs
    public static <T> String[] of(@NonNull String[] what, @NonNull String[] target, @NonNull T... with)
    {
        checkArgument(target.length == with.length, ErrorMessages.ARGS_NOT_SAME_SIZE.get());

        for (int i = 0; i < what.length; i++)
        {
            for (int j = 0; j < target.length; j++)
            {
                what[i] = what[i].replaceAll(target[j], with[j].toString());
            }
        }

        return what;
    }

    @SuppressWarnings("unchecked")
    public static <W extends Collection<String>, T> W of(@NonNull W what, @NonNull String target, @NonNull T with)
    {
        return (W) what.stream().map(s -> s.replaceAll(target, with.toString())).collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    public static <W extends Collection<String>, T> W of(@NonNull W what, @NonNull String[] target, @NonNull T... with)
    {
        checkArgument(target.length == with.length, ErrorMessages.ARGS_NOT_SAME_SIZE.get());

        return (W) what.stream().map(s ->
        {
            for (int i = 0; i < target.length; i++)
            {
                s = s.replaceAll(target[i], with[i].toString());
            }

            return s;
        }).collect(Collectors.toList());
    }

}
