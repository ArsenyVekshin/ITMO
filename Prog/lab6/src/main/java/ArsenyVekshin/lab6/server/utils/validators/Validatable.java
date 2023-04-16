package ArsenyVekshin.lab6.server.utils.validators;

public interface Validatable {
    <T> boolean validate(T value) throws IllegalArgumentException;
    Validatable getInstance();
}
