package ArsenyVekshin.lab7.common.annotations;

public interface Validatable {
    <T> boolean validate(T value) throws IllegalArgumentException;
    Validatable getInstance();
}
