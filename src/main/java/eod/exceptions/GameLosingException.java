package eod.exceptions;

public class GameLosingException extends RuntimeException {
    public GameLosingException(String cause) {
        super(cause);
    }
}
