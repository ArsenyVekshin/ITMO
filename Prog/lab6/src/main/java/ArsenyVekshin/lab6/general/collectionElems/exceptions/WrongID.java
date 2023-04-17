package ArsenyVekshin.lab6.general.collectionElems.exceptions;

import java.io.IOException;

/**
 * answered id not found
 */
public class WrongID extends IOException {
    public WrongID(Long id) { super("Entered id(" + id.toString() + ") not defined"); }
}
