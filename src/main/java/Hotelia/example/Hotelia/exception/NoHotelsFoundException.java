package Hotelia.example.Hotelia.exception;

public class NoHotelsFoundException extends RuntimeException{
    public NoHotelsFoundException(String message) {
        super(message);
    }
}
