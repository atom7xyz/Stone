package xyz.sorridi.stone.utils.constructor;

import xyz.sorridi.stone.immutable.ErrorMessages;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class ConstructorCaller
{

    public static <T> Optional<T> call(Class<T> clazz, Object... args)
    {
        return call(clazz, 0, args);
    }

    public static <T> Optional<T> call(Class<T> clazz, int index, Object... args)
    {
        checkNotNull(clazz, ErrorMessages.NULL);
        checkArgument(index >= 0, ErrorMessages.NEGATIVE);

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
