package xyz.sorridi.stone.annotations;

import java.lang.annotation.Annotation;

/**
 * Interface used to execute actions given resources.
 * @author Sorridi
 * @since 1.0
 */
public interface IResourceAction
{
    /**
     * Executes the action given a resource.
     * @param annotation The annotation.
     * @param found The resource.
     */
    void expression(Annotation annotation, Object found);
}
