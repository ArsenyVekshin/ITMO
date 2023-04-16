package ArsenyVekshin.lab6.server.ui.exeptions;

import java.io.IOException;

/**
 * target string is null
 */
public class NotNullException extends IOException {
    public NotNullException() { super("Entered string is null"); }
}
