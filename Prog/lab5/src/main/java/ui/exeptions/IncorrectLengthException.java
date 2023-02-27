package ui.exeptions;

import java.io.IOException;

public class IncorrectLengthException extends IOException {
    public IncorrectLengthException() { super("Entered string is to large"); }
}
