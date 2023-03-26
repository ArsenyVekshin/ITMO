package ArsenyVekshin.lab5.collection.exceptions;

import java.io.IOException;

public class NoneValueFromCSV extends IOException {
    public NoneValueFromCSV(String param) { super("csv import error, received string:" + param); }
}
