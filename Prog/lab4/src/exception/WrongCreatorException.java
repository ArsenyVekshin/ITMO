package exception;

import java.io.IOException;

public class WrongCreatorException extends IOException {
    public WrongCreatorException(String message){
        super(message);
    }
}