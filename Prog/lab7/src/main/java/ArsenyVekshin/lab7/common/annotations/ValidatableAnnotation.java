package ArsenyVekshin.lab7.common.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation that all validator annotations must have
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidatableAnnotation {
    public Class<? extends Validatable> validator();
}
