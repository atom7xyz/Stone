package xyz.sorridi.stone.common.utils.constructor;

import lombok.NonNull;
import xyz.sorridi.stone.common.immutable.Err;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Constructor caller utilities.
 * <p>
 * This utility class provides methods to call a constructor of a class with the given arguments.
 * It uses reflection to instantiate objects dynamically.
 * </p>
 *
 * @author atom7xyz
 * @since 1.0
 */
public class ConstructorCaller
{

    /**
     * Calls a constructor with the given parameters.
     *
     * @param clazz The class whose constructor should be called.
     * @param args  The arguments to pass to the constructor.
     * @param <T>   The type of the class.
     * @return An Optional containing the created object, or an empty Optional if instantiation fails.
     */
    public static <T> Optional<T> call(Class<T> clazz, Object... args)
    {
        return call(clazz, 0, args);
    }

    /**
     * Calls a specific constructor with the given parameters.
     *
     * @param clazz The class whose constructor should be called.
     * @param index The index of the constructor to call (if multiple constructors exist).
     * @param args  The arguments to pass to the constructor.
     * @param <T>   The type of the class.
     * @return An Optional containing the created object, or an empty Optional if instantiation fails.
     */
    public static <T> Optional<T> call(@NonNull Class<T> clazz, int index, Object... args)
    {
        checkArgument(index >= 0, Err.NEGATIVE.expect("index"));

        Optional<T> instance = Optional.empty();

        try
        {
            T constructor = (T) clazz.getConstructors()[index].newInstance(args);
            instance = Optional.of(constructor);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return instance;
    }

}
