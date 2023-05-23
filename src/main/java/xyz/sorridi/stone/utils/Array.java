package xyz.sorridi.stone.utils;

import lombok.NonNull;

import java.util.Arrays;
import java.util.Collection;

/**
 * Array utilities.
 * @author Sorridi
 * @since 1.0
 */
public class Array
{

    /**
     * Creates an array from the given elements.
     * @param elements The elements to put in the array.
     * @return The created array.
     * @param <T> The type of the elements.
     */
    @SafeVarargs
    public static <T> T[] of(@NonNull T... elements)
    {
        return elements;
    }

    /**
     * Creates an array from the given list.
     * @param list The list to put in the array.
     * @param clazz The class of the array.
     * @return The created array.
     */
    public static <T, L extends Collection<T>> T[] of(L list, Class<T[]> clazz)
    {
        return clazz.cast(Arrays.copyOf(list.toArray(), list.size(), clazz));
    }

}
