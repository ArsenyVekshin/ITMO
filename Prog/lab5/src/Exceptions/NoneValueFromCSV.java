package Exceptions;

import java.io.IOException;

public class NoneValueFromCSV extends IOException {
    public NoneValueFromCSV(String className, String param) { super(className + ": csv import error, received string:" + param); }
}
