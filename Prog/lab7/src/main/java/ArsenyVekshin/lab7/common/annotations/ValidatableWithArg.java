package ArsenyVekshin.lab7.common.annotations;

/**
 * Interface for fields validator that has argument
 */
public interface ValidatableWithArg extends Validatable {
    Validatable getInstance(String value);
}
