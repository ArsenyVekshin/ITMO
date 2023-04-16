package ArsenyVekshin.lab6.client.utils.validators;

public interface Validatable {
    <T> boolean validate(T value) throws IllegalArgumentException;
    Validatable getInstance();
}
