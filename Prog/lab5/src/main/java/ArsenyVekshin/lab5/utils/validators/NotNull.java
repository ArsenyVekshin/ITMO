package ArsenyVekshin.lab5.utils.validators;

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@ValidatableAnnotation(validator = NotNullValidator.class)
public @interface NotNull {
}