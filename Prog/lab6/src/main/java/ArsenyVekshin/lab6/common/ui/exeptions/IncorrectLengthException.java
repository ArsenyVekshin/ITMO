package ArsenyVekshin.lab6.common.ui.exeptions;

import java.io.IOException;

/**
 * Target string length so big
 */
public class IncorrectLengthException extends IOException {
    public IncorrectLengthException() { super("Entered string is to large"); }
}
