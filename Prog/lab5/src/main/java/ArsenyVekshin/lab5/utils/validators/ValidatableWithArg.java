package ArsenyVekshin.lab5.utils.validators;

/**
 * Interface for fields validator that has argument
 */
public interface ValidatableWithArg extends Validatable{
    Validatable getInstance(String value);
}
