package ArsenyVekshin.lab7.common.annotations;

import java.lang.annotation.*;

/**
 * Annotation for validate that field grater or equal than certain value
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@ValidatableAnnotation(validator = GreaterThanValidator.class)
public @interface GreaterThan {
    public String value() default "0";
}
