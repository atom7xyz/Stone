package xyz.sorridi.stone.annotations.impl.serializer;

import javax.annotation.CheckForNull;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to register custom serializers.
 * @author Sorridi
 * @since 1.0
 */
@SupportedSourceVersion(SourceVersion.RELEASE_17)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RegisterSerializer
{
    /**
     * The type of serializer.
     * @return The type of serializer.
     */
    @CheckForNull Class<?> of();
}
