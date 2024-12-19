package xyz.sorridi.stone.common.annotations;

import lombok.SneakyThrows;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import xyz.sorridi.stone.common.immutable.Err;
import xyz.sorridi.stone.common.utils.data.Array;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Class used to gather resources via Reflection.
 *
 * @author atom7xyz
 * @since 1.0
 */
public class ResourceGatherer
{
    private static final Scanners[] SCANNERS = Array.of(Scanners.TypesAnnotated,
                                                        Scanners.SubTypes,
                                                        Scanners.MethodsAnnotated,
                                                        Scanners.FieldsAnnotated);

    /**
     * Executes a given action for each annotation found.
     *
     * @param annotation The annotation.
     * @param action     The action.
     * @param type       The element type.
     * @param base       The class to search into.
     */
    @SneakyThrows
    public static <A extends Annotation, CA extends Class<A>> void forEachAnnotation(
            CA annotation,
            BiConsumer<A, Object> action,
            ElementType type,
            Class<?> base)
    {
        Reflections reflections = new Reflections(getPath(base), SCANNERS);

        Set<?> found;

        switch (type)
        {
            case METHOD -> found = reflections.getMethodsAnnotatedWith(annotation);
            case FIELD -> found = reflections.getFieldsAnnotatedWith(annotation);
            case TYPE -> found = reflections.getTypesAnnotatedWith(annotation);
            default -> throw new IllegalArgumentException(Err.NOT_IMPLEMENTED.get());
        }

        found.forEach(elem ->
                      {
                          A foundAnnotation = null;

                          switch (type)
                          {
                              case METHOD -> foundAnnotation = ((Method) elem).getAnnotation(annotation);
                              case FIELD -> foundAnnotation = ((Field) elem).getAnnotation(annotation);
                              case TYPE -> foundAnnotation = ((Class<?>) elem).getAnnotation(annotation);
                          }

                          action.accept(foundAnnotation, elem);
                      });
    }

    /**
     * Returns the file path of a given class.
     *
     * @param clazz The class.
     * @return The file path.
     */
    public static URL getPath(Class<?> clazz)
    {
        return clazz.getProtectionDomain().getCodeSource().getLocation();
    }

    /**
     * Returns the methods of a given class.
     *
     * @param clazz The class.
     * @return The methods.
     */
    public static List<Method> getMethods(Class<?> clazz)
    {
        return List.of(clazz.getDeclaredMethods());
    }

    /**
     * Returns the methods of a given object.
     *
     * @param object The object.
     * @return The methods.
     */
    public static List<Method> getMethods(Object object)
    {
        return getMethods(object.getClass());
    }

    /**
     * Returns the stream of methods of a given class.
     *
     * @param clazz The class.
     * @return The methods.
     */
    public static Stream<Method> streamMethods(Class<?> clazz)
    {
        return getMethods(clazz).stream();
    }

    /**
     * Returns the stream of methods of a given object.
     *
     * @param object The object.
     * @return The methods.
     */
    public static Stream<Method> streamMethods(Object object)
    {
        return getMethods(object).stream();
    }

    /**
     * Executes a given action for each method of a given class.
     *
     * @param clazz    The class.
     * @param consumer The action.
     */
    public static void forEachMethod(Class<?> clazz, Consumer<Method> consumer)
    {
        getMethods(clazz).forEach(consumer);
    }

    /**
     * Executes a given action for each method of a given object.
     *
     * @param object   The object.
     * @param consumer The action.
     */
    public static void forEachMethod(Object object, Consumer<Method> consumer)
    {
        getMethods(object).forEach(consumer);
    }

    /**
     * Executes a given action for each method of a given class.
     *
     * @param clazz    The class.
     * @param consumer The action.
     */
    @SafeVarargs
    public static void forEachMethod(Class<?> clazz, Consumer<Method> consumer, Predicate<Method>... predicates)
    {
        streamMethods(clazz)
                .filter(method ->
                        {
                            for (var predicate : predicates)
                                if (!predicate.test(method))
                                    return false;

                            return true;
                        })
                .forEach(consumer);
    }

    /**
     * Executes a given action for each method of a given object.
     *
     * @param object   The object.
     * @param consumer The action.
     */
    @SafeVarargs
    public static void forEachMethod(Object object, Consumer<Method> consumer, Predicate<Method>... predicates)
    {
        forEachMethod(object.getClass(), consumer, predicates);
    }

}
