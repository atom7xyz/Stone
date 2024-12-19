package xyz.sorridi.stone.common.utils.data;

import lombok.NonNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

/**
 * Array utilities.
 * <p>
 * This class provides methods to work with arrays and collections, such as creating arrays from elements or lists,
 * checking if arrays or collections are empty, and wrapping arrays in a custom wrapper class.
 * </p>
 *
 * @author atom7xyz
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
     * @return True if the array is empty, false otherwise.
     */
    public static boolean isEmpty(Object[] array)
    {
        return array == null || array.length == 0;
    }

    /**
     * Checks if the given collection is empty.
     *
     * @param list The collection to check.
     * @return True if the collection is empty, false otherwise.
     */
    public static boolean isEmpty(Collection<?> list)
    {
        return list == null || list.isEmpty();
    }

    /**
     * Wrapper for any type of Object, allowing for custom equality and string representation.
     */
    public static class Wrapper
    {
        private Object array;

        /**
         * Constructor to initialize the wrapper with an array of objects.
         *
         * @param array The array to wrap.
         */
        public Wrapper(Object... array)
        {
            this.array = array;
        }

        /**
         * Gets the array.
         *
         * @return The array.
         */
        public Object get()
        {
            return array;
        }

        /**
         * Sets the array.
         *
         * @param array The array to set.
         * @return The wrapper.
         */
        public Wrapper set(Object array)
        {
            this.array = array;
            return this;
        }

        /**
         * Compares this wrapper to another object for equality.
         *
         * @param o The object to compare.
         * @return True if the objects are equal, false otherwise.
         */
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

            return Objects.deepEquals(array, ((Wrapper) o).array);
        }

        /**
         * Returns the hash code for this wrapper.
         *
         * @return The hash code of the wrapped array.
         */
        @Override
        public int hashCode()
        {
            return Arrays.deepHashCode((Object[]) array);
        }

        /**
         * Returns a string representation of the wrapper.
         *
         * @return A string representation of the wrapped array.
         */
        @Override
        public String toString()
        {
            return Arrays.deepToString((Object[]) array);
        }
    }

}
