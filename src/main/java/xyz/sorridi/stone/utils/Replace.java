package xyz.sorridi.stone.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Replacements utilities for strings.
 * @author Sorridi
 * @since 1.0
 */
public class Replace
{

    public static <T> String of(String what, String target, T with)
    {
        return what.replaceAll(target, with.toString());
    }

    public static <T> String of(String what, String[] target, T[] with)
    {
        for (int i = 0; i < target.length; i++)
        {
            what = what.replaceAll(target[i], with[i].toString());
        }

        return what;
    }

    public static <T> String[] of(String[] what, String target, T with)
    {
        return Arrays.stream(what).map(s -> s.replaceAll(target, with.toString())).toArray(String[]::new);
    }

    public static <T> String[] of(String[] what, String[] target, T[] with)
    {
        for (int i = 0; i < what.length; i++)
        {
            for (int j = 0; j < target.length; j++)
            {
                what[i] = what[i].replaceAll(target[j], with[j].toString());
            }
        }

        return what;
    }

    public static <W extends Collection<String>, T> W of(W what, String target, T with)
    {
        return (W) what.stream().map(s -> s.replaceAll(target, with.toString())).collect(Collectors.toList());
    }

    public static <W extends Collection<String>, T> W of(W what, String[] target, T[] with)
    {
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
