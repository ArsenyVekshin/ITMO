package org.com.collection.exceptions;

import java.io.IOException;

public class WrongID extends IOException {
    public WrongID(Long id) { super("Entered id(" + id.toString() + ") not defined"); }
}
