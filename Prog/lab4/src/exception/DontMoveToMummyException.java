package exception;

import java.io.IOException;

public class DontMoveToMummyException extends IOException{
    public DontMoveToMummyException(String message){
        super(message);
    }
}