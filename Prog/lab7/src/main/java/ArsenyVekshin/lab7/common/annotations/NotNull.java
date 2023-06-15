package ArsenyVekshin.lab7.common.annotations;

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@ValidatableAnnotation(validator = NotNullValidator.class)
public @interface NotNull {
}