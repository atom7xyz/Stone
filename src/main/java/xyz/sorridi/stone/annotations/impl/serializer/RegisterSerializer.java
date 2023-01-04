package xyz.sorridi.stone.annotations.impl.serializer;

import javax.annotation.CheckForNull;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@SupportedSourceVersion(SourceVersion.RELEASE_17)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RegisterSerializer
{
    @CheckForNull Class<?> of();
}
