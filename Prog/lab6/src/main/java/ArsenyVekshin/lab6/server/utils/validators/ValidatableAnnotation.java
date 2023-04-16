package ArsenyVekshin.lab6.server.utils.validators;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation that all validator annotations must have
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidatableAnnotation {
    public Class<? extends Validatable> validator();
}
