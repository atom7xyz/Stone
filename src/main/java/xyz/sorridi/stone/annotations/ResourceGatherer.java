package xyz.sorridi.stone.annotations;

import lombok.SneakyThrows;
import org.reflections.Reflections;
import xyz.sorridi.stone.immutable.ErrorMessages;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Set;

/**
 * Class used to gather resources.
 * @author Sorridi
 * @since 1.0
 */
public class ResourceGatherer
{

    /**
     * Executes a given action for each annotation found.
     * @param annotation The annotation.
     * @param action The action.
     * @param type The element type.
     * @param base The class to search into.
     */
    @SneakyThrows
    public static void forEachAnnotationInTypes(
            Class<? extends Annotation> annotation,
            IResourceAction action,
            ElementType type,
            Class<?> base
    ) {
        Reflections reflections = new Reflections(getPath(base));

        Set<?> found;

        switch (type)
        {
            case METHOD -> found = reflections.getMethodsAnnotatedWith(annotation);
            case FIELD  -> found = reflections.getFieldsAnnotatedWith(annotation);
            case TYPE   -> found = reflections.getTypesAnnotatedWith(annotation);
            default     -> throw new IllegalArgumentException(ErrorMessages.NOT_IMPLEMENTED.getMessage());
        }

        found.forEach(foundClass ->
        {
            Annotation foundAnnotation = null;

            switch (type)
            {
                case METHOD -> foundAnnotation = ((Method) foundClass).getAnnotation(annotation);
                case FIELD  -> foundAnnotation = ((Field) foundClass).getAnnotation(annotation);
                case TYPE   -> foundAnnotation = ((Class<?>) foundClass).getAnnotation(annotation);
            }

            action.expression(foundAnnotation, foundClass);
        });
    }

    public static URL getPath(Class<?> clazz)
    {
        return clazz.getProtectionDomain().getCodeSource().getLocation();
    }

}
