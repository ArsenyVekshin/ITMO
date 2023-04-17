package ArsenyVekshin.lab6.general.collectionElems.exceptions;

import java.io.IOException;

/**
 * entered value isn't correct for field exception
 */
public class InvalidValueEntered extends IOException {
    public InvalidValueEntered(String className, String param) { super(className + ": введенное значение " + param + " некорректно"); }
}
