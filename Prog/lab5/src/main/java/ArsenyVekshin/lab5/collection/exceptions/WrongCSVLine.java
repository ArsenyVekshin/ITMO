package ArsenyVekshin.lab5.collection.exceptions;

import java.io.IOException;

public class WrongCSVLine extends IOException {
    public WrongCSVLine(String param) { super("csv import error, not enough params on line: " + param); }
}
