package ArsenyVeksin.lab4.backend.exceptions;

import java.io.IOException;

public class OutOfBoundException extends IOException {
    public OutOfBoundException() {
        super("Entered point is out of bounds of the coordinate plane");
    }
}
