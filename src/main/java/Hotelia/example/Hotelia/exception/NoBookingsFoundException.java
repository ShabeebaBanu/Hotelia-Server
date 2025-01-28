package Hotelia.example.Hotelia.exception;

public class NoBookingsFoundException extends RuntimeException {
    public NoBookingsFoundException(String message) {
        super(message);
    }
}
