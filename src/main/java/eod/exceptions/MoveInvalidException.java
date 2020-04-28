package eod.exceptions;

public class MoveInvalidException extends IllegalArgumentException{
    public MoveInvalidException(String cause) {
        super(cause);
    }
}
