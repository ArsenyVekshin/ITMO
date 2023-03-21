package collection.exceptions;

import java.io.IOException;

public class InvalidValueEntered extends IOException {
    public InvalidValueEntered(String className, String param) { super(className + ": введенное значение " + param + " некорректно"); }
}
