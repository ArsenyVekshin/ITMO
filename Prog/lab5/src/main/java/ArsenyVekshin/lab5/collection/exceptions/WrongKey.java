package ArsenyVekshin.lab5.collection.exceptions;

import java.io.IOException;

public class WrongKey extends IOException {
    public WrongKey(String key) { super("Entered value-key(" + key + ") not defined"); }
}
