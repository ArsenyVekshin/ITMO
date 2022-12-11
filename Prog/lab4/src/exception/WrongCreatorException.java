package exception;
public class WrongCreatorException extends RuntimeException {
    public WrongCreatorException(String message){
        super(message);
    }
}