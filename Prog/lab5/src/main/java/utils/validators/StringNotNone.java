package utils.validators;

import java.lang.annotation.*;

/**
 * Annotation for validate that fields value isn't empty string
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@ValidatableAnnotation(validator = StringNotNoneValidator.class)
public @interface StringNotNone {
}
