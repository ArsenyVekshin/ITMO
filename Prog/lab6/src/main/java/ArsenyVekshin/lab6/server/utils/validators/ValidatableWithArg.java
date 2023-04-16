package ArsenyVekshin.lab6.server.utils.validators;

/**
 * Interface for fields validator that has argument
 */
public interface ValidatableWithArg extends Validatable{
    Validatable getInstance(String value);
}
