package xyz.sorridi.stone.utils.constructor;

import lombok.NonNull;
import xyz.sorridi.stone.immutable.ErrorMessages;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Constructor caller utilities.
 * @author Sorridi
 * @since 1.0
 */
public class ConstructorCaller
{

    /**
     * Calls a constructor with the given parameters.
     * @param clazz The class to call.
     * @param args The arguments to pass.
     * @return The created object.
     * @param <T> The type of the class.
     */
    public static <T> Optional<T> call(Class<T> clazz, Object... args)
    {
        return call(clazz, 0, args);
    }

    /**
     * Calls a constructor with the given parameters.
     * @param clazz The class to call.
     * @param index The index of the constructor to call.
     * @param args The arguments to pass.
     * @return The created object.
     * @param <T> The type of the class.
     */
    public static <T> Optional<T> call(@NonNull Class<T> clazz, int index, Object... args)
    {
        checkArgument(index >= 0, ErrorMessages.NEGATIVE.get());

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
