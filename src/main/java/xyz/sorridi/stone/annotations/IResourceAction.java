package xyz.sorridi.stone.annotations;

import java.lang.annotation.Annotation;

public interface IResourceAction
{
    void expression(Annotation annotation, Object found);
}
