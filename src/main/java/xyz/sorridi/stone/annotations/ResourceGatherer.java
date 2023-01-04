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

public class ResourceGatherer
{

    @SneakyThrows
    public static <T> void forEachAnnotationInTypes(
            Class<? extends Annotation> clazz,
            IResourceAction action,
            ElementType type,
            Class<?> base
    ) {
        Reflections reflections = new Reflections(getPath(base));

        Set<?> found;

        switch (type)
        {
            case METHOD -> found = reflections.getMethodsAnnotatedWith(clazz);
            case FIELD  -> found = reflections.getFieldsAnnotatedWith(clazz);
            case TYPE   -> found = reflections.getTypesAnnotatedWith(clazz);
            default     -> throw new IllegalArgumentException(ErrorMessages.NOT_IMPLEMENTED.getMessage());
        }

        found.forEach(foundClass ->
        {
            Annotation annotation = null;

            switch (type)
            {
                case METHOD -> annotation = ((Method) foundClass).getAnnotation(clazz);
                case FIELD  -> annotation = ((Field) foundClass).getAnnotation(clazz);
                case TYPE   -> annotation = ((Class<?>) foundClass).getAnnotation(clazz);
            }

            action.expression(annotation, foundClass);
        });
    }

    public static URL getPath(Class<?> clazz)
    {
        return clazz.getProtectionDomain().getCodeSource().getLocation();
    }

}
