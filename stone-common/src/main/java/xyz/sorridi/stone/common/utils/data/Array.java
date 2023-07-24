package xyz.sorridi.stone.common.utils.data;

import lombok.NonNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

/**
 * Array utilities.
 *
 * @author Sorridi
 * @since 1.0
 */
public class Array
{

    /**
     * Creates an array from the given elements.
     *
     * @param elements The elements to put in the array.
     * @param <T>      The type of the elements.
     * @return The created array.
     */
    @SafeVarargs
    public static <T> T[] of(@NonNull T... elements)
    {
        return elements;
    }

    /**
     * Creates an array from the given list.
     *
     * @param list  The list to put in the array.
     * @param clazz The class of the array.
     * @return The created array.
     */
    public static <T, L extends Collection<T>> T[] of(L list, Class<T[]> clazz)
    {
        return clazz.cast(Arrays.copyOf(list.toArray(), list.size(), clazz));
    }

    /**
     * Checks if the given array is empty.
     *
     * @param array The array to check.
     * @return If the array is empty.
     */
    public static boolean isEmpty(Object[] array)
    {
        return array == null || array.length == 0;
    }

    /**
     * Checks if the given collection is empty.
     *
     * @param list The collection to check.
     * @return If the collection is empty.
     */
    public static boolean isEmpty(Collection<?> list)
    {
        return list == null || list.isEmpty();
    }

    public static class Wrapper
    {
        private Object array;

        public Wrapper(Object... array)
        {
            this.array = array;
        }

        public Object get()
        {
            return array;
        }

        public Wrapper set(Object array)
        {
            this.array = array;
            return this;
        }

        @Override
        public boolean equals(Object o)
        {
            if (this == o)
            {
                return true;
            }

            if (o == null || getClass() != o.getClass())
            {
                return false;
            }

            Wrapper that = (Wrapper) o;
            return Objects.deepEquals(array, that.array);
        }

        @Override
        public int hashCode()
        {
            return Arrays.deepHashCode((Object[]) array);
        }

        @Override
        public String toString()
        {
            return Arrays.deepToString((Object[]) array);
        }
    }

}
