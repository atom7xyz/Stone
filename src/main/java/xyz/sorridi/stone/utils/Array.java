package xyz.sorridi.stone.utils;

import lombok.NonNull;

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
     * Creates an array from the given collection.
     * @param collection The collection to put in the array.
     * @return The created array.
     * @param <T> The type of the elements.
     * @param <C> The type of the collection.
     */
    @SuppressWarnings("unchecked")
    public static <T, C extends Collection<T>> T[] of (C collection)
    {
        return (T[]) collection.toArray();
    }

}
