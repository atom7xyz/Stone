package xyz.sorridi.stone.annotations;

import lombok.SneakyThrows;
import org.checkerframework.checker.units.qual.C;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;
import xyz.sorridi.stone.immutable.ErrorMessages;
import xyz.sorridi.stone.utils.Array;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Set;
import java.util.function.BiConsumer;

/**
 * Class used to gather resources.
 * @author Sorridi
 * @since 1.0
 */
public class ResourceGatherer
{
    private static final Scanners[] SCANNERS = Array.of(
            Scanners.TypesAnnotated,
            Scanners.SubTypes,
            Scanners.MethodsAnnotated,
            Scanners.FieldsAnnotated
    );

    /**
     * Executes a given action for each annotation found.
     * @param annotation The annotation.
     * @param action The action.
     * @param type The element type.
     * @param base The class to search into.
     */
    @SneakyThrows
    public static <A extends Annotation, CA extends Class<A>> void forEachAnnotation(
            CA annotation,
            BiConsumer<A, Object> action,
            ElementType type,
            Class<?> base
    ) {
        Reflections reflections = new Reflections(getPath(base), SCANNERS);

        Set<?> found;

        switch (type)
        {
            case METHOD -> found = reflections.getMethodsAnnotatedWith(annotation);
            case FIELD  -> found = reflections.getFieldsAnnotatedWith(annotation);
            case TYPE   -> found = reflections.getTypesAnnotatedWith(annotation);
            default     -> throw new IllegalArgumentException(ErrorMessages.NOT_IMPLEMENTED.get());
        }

        found.forEach(elem ->
        {
            A foundAnnotation = null;

            switch (type)
            {
                case METHOD -> foundAnnotation = ((Method) elem).getAnnotation(annotation);
                case FIELD  -> foundAnnotation = ((Field) elem).getAnnotation(annotation);
                case TYPE   -> foundAnnotation = ((Class<?>) elem).getAnnotation(annotation);
            }

            action.accept(foundAnnotation, elem);
        });
    }

    /**
     * Returns the file path of a given class.
     * @param clazz The class.
     * @return The file path.
     */
    public static URL getPath(Class<?> clazz)
    {
        return clazz.getProtectionDomain().getCodeSource().getLocation();
    }

}
